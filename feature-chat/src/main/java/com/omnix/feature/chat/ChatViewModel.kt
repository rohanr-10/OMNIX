package com.omnix.feature.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omnix.core.data.ai.AiResponseProvider
import com.omnix.core.data.repository.ChatRepository
import com.omnix.core.model.AiMode
import com.omnix.core.model.ChatMessage
import com.omnix.core.model.MessageRole
import com.omnix.core.model.MessageStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val aiResponseProvider: AiResponseProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val sessionId: String = savedStateHandle.get<String>("sessionId").orEmpty()

    private val _uiState = MutableStateFlow(ChatUiState(sessionId = sessionId))
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        observeSession()
    }

    private fun observeSession() {
        if (sessionId.isBlank()) {
            _uiState.update { it.copy(isLoadingHistory = false) }
            return
        }
        viewModelScope.launch {
            chatRepository.observeMessages(sessionId).collect { messages ->
                _uiState.update {
                    it.copy(isLoadingHistory = false, messages = messages)
                }
            }
        }
    }

    fun onDraftChanged(text: String) {
        _uiState.update { it.copy(draftText = text) }
    }

    fun onModeSelected(mode: AiMode) {
        _uiState.update { it.copy(selectedMode = mode) }
    }

    fun sendMessage() {
        val state = _uiState.value
        val text = state.draftText.trim()
        if (text.isBlank() || state.isAssistantResponding) return

        _uiState.update { it.copy(draftText = "", isAssistantResponding = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                chatRepository.sendMessage(sessionId, text, state.selectedMode)
                streamAssistantReply(prompt = text, mode = state.selectedMode)
            } catch (t: Throwable) {
                _uiState.update {
                    it.copy(isAssistantResponding = false, errorMessage = t.message ?: "Something went wrong")
                }
            }
        }
    }

    private suspend fun streamAssistantReply(prompt: String, mode: AiMode) {
        val assistantMessageId = UUID.randomUUID().toString()
        val history = _uiState.value.messages.map { it.content }

        var latestChunk = ""
        aiResponseProvider.streamResponse(prompt, mode, history).collect { chunk ->
            latestChunk = chunk
            upsertStreamingAssistantMessage(assistantMessageId, chunk, mode, isFinal = false)
        }

        upsertStreamingAssistantMessage(assistantMessageId, latestChunk, mode, isFinal = true)
        _uiState.update { it.copy(isAssistantResponding = false) }
    }

    /**
     * Updates the in-memory message list immediately for smooth streaming UI,
     * and persists the final state to the repository once streaming completes.
     */
    private suspend fun upsertStreamingAssistantMessage(
        messageId: String,
        content: String,
        mode: AiMode,
        isFinal: Boolean
    ) {
        val now = System.currentTimeMillis()
        val existing = _uiState.value.messages.firstOrNull { it.id == messageId }
        val updatedMessage = (existing ?: ChatMessage(
            id = messageId,
            sessionId = sessionId,
            role = MessageRole.ASSISTANT,
            content = "",
            timestamp = now,
            mode = mode,
            status = MessageStatus.STREAMING
        )).copy(
            content = content,
            status = if (isFinal) MessageStatus.COMPLETE else MessageStatus.STREAMING
        )

        _uiState.update { state ->
            val withoutExisting = state.messages.filterNot { it.id == messageId }
            state.copy(messages = withoutExisting + updatedMessage)
        }

        if (isFinal) {
            chatRepository.saveMessage(updatedMessage)
        }
    }

    /**
     * Manual Council trigger: saves the current draft as a user message
     * tagged with the AI Council mode, then hands the prompt back to the
     * caller (the NavHost) so it can navigate to the dedicated node-graph
     * screen, which runs the actual deliberation and writes the consensus
     * back into this session once it finishes.
     */
    fun startCouncilDeliberation(onNavigate: (prompt: String) -> Unit) {
        val text = _uiState.value.draftText.trim()
        if (text.isBlank() || _uiState.value.isAssistantResponding) return

        _uiState.update { it.copy(draftText = "") }
        viewModelScope.launch {
            chatRepository.sendMessage(sessionId, text, AiMode.AI_COUNCIL)
            onNavigate(text)
        }
    }

    fun regenerateLastResponse() {
        val lastUserMessage = _uiState.value.messages.lastOrNull { it.role == MessageRole.USER }
            ?: return
        if (_uiState.value.isAssistantResponding) return

        _uiState.update { it.copy(isAssistantResponding = true) }
        viewModelScope.launch {
            streamAssistantReply(prompt = lastUserMessage.content, mode = lastUserMessage.mode)
        }
    }

    fun deleteMessage(messageId: String) {
        viewModelScope.launch {
            chatRepository.deleteMessage(messageId)
        }
    }

    fun editMessage(messageId: String, newContent: String) {
        viewModelScope.launch {
            chatRepository.updateMessageContent(messageId, newContent)
        }
    }
}
