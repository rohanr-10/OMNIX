package com.omnix.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omnix.core.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeRecentSessions()
    }

    private fun observeRecentSessions() {
        viewModelScope.launch {
            chatRepository.observeActiveSessions()
                .catch { _uiState.value = _uiState.value.copy(isLoading = false) }
                .collect { sessions ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        recentSessions = sessions.take(5)
                    )
                }
        }
    }

    fun createNewChat(onCreated: (sessionId: String) -> Unit) {
        viewModelScope.launch {
            val session = chatRepository.createSession(title = "New Chat")
            onCreated(session.id)
        }
    }
}
