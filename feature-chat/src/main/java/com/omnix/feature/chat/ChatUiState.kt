package com.omnix.feature.chat

import com.omnix.core.model.AiMode
import com.omnix.core.model.ChatMessage

data class ChatUiState(
    val sessionId: String = "",
    val sessionTitle: String = "New Chat",
    val isLoadingHistory: Boolean = true,
    val messages: List<ChatMessage> = emptyList(),
    val draftText: String = "",
    val selectedMode: AiMode = AiMode.QUICK,
    val isAssistantResponding: Boolean = false,
    val errorMessage: String? = null
)

/**
 * The scrollable mode chips shown above the composer. Order here drives
 * the order they render in, matching the master spec:
 * Quick, Deep Think, AI Council, Deep Research, Creative, Engineer,
 * Research, Explain, Summarize, Translate, Solve.
 */
val ChatModeChipOrder: List<AiMode> = listOf(
    AiMode.QUICK,
    AiMode.DEEP_THINK,
    AiMode.AI_COUNCIL,
    AiMode.DEEP_RESEARCH,
    AiMode.CREATIVE,
    AiMode.ENGINEER,
    AiMode.RESEARCH,
    AiMode.EXPLAIN,
    AiMode.SUMMARIZE,
    AiMode.TRANSLATE,
    AiMode.SOLVE
)
