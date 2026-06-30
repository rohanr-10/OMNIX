package com.omnix.feature.council

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omnix.core.data.council.CouncilOrchestrator
import com.omnix.core.data.repository.ChatRepository
import com.omnix.core.model.AgentContribution
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
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CouncilViewModel @Inject constructor(
    private val orchestrator: CouncilOrchestrator,
    private val chatRepository: ChatRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val sessionId: String = savedStateHandle.get<String>("sessionId").orEmpty()
    private val encodedPrompt: String = savedStateHandle.get<String>("prompt").orEmpty()
    private val prompt: String = runCatching {
        URLDecoder.decode(encodedPrompt, StandardCharsets.UTF_8.toString())
    }.getOrDefault(encodedPrompt)

    private val assistantMessageId = UUID.randomUUID().toString()

    private val _uiState = MutableStateFlow(CouncilUiState(prompt = prompt))
    val uiState: StateFlow<CouncilUiState> = _uiState.asStateFlow()

    init {
        runDeliberation()
    }

    private fun runDeliberation() {
        if (prompt.isBlank()) {
            _uiState.update { it.copy(isRunning = false, isComplete = true) }
            return
        }
        viewModelScope.launch {
            orchestrator.runCouncil(
                prompt = prompt,
                messageId = assistantMessageId
            ).collect { session ->
                val consensusText = session.consensusText
                _uiState.update {
                    it.copy(
                        isRunning = !session.isComplete,
                        isComplete = session.isComplete,
                        nodes = session.nodes,
                        consensusText = consensusText,
                        contributions = session.contributions
                    )
                }
                if (session.isComplete && consensusText != null) {
                    persistConsensus(consensusText, session.contributions)
                }
            }
        }
    }

    private suspend fun persistConsensus(consensusText: String, contributions: List<AgentContribution>) {
        if (sessionId.isBlank()) return
        val message = ChatMessage(
            id = assistantMessageId,
            sessionId = sessionId,
            role = MessageRole.ASSISTANT,
            content = consensusText,
            timestamp = System.currentTimeMillis(),
            mode = AiMode.AI_COUNCIL,
            status = MessageStatus.COMPLETE,
            councilContribution = contributions
        )
        chatRepository.saveMessage(message)
    }

    fun inspectNode(contribution: AgentContribution?) {
        _uiState.update { it.copy(selectedRoleForInspection = contribution) }
    }
}