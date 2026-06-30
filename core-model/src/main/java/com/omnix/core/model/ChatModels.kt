package com.omnix.core.model

/**
 * Represents a single message within a chat session.
 */
data class ChatMessage(
    val id: String,
    val sessionId: String,
    val role: MessageRole,
    val content: String,
    val timestamp: Long,
    val mode: AiMode = AiMode.QUICK,
    val status: MessageStatus = MessageStatus.COMPLETE,
    val attachments: List<Attachment> = emptyList(),
    val councilContribution: List<AgentContribution>? = null,
    val isEdited: Boolean = false,
    val parentMessageId: String? = null
)

enum class MessageRole {
    USER,
    ASSISTANT,
    SYSTEM
}

enum class MessageStatus {
    PENDING,
    STREAMING,
    COMPLETE,
    ERROR,
    CANCELLED
}

data class Attachment(
    val id: String,
    val uri: String,
    val type: AttachmentType,
    val displayName: String,
    val sizeBytes: Long
)

enum class AttachmentType {
    IMAGE,
    PDF,
    DOCUMENT,
    AUDIO,
    OTHER
}

/**
 * A chat session groups a sequence of messages under one conversation thread.
 */
data class ChatSession(
    val id: String,
    val title: String,
    val createdAt: Long,
    val updatedAt: Long,
    val pinned: Boolean = false,
    val archived: Boolean = false,
    val messageCount: Int = 0,
    val lastMessagePreview: String = ""
)

/**
 * Execution strategy selected by the user for a given turn or session.
 */
enum class AiMode(val displayName: String, val description: String) {
    QUICK("Quick", "Fast, lightweight responses for everyday questions"),
    DEEP_THINK("Deep Think", "Extended reasoning for complex problems"),
    AI_COUNCIL("AI Council", "Multiple specialized agents collaborate on a consensus answer"),
    DEEP_RESEARCH("Deep Research", "Thorough, multi-step research with source gathering"),
    CREATIVE("Creative", "Open-ended, imaginative generation"),
    ENGINEER("Engineer", "Code-focused reasoning and implementation"),
    RESEARCH("Research", "Structured analysis and fact gathering"),
    EXPLAIN("Explain", "Step-by-step explanations tuned for clarity"),
    SUMMARIZE("Summarize", "Concise distillation of long content"),
    TRANSLATE("Translate", "Cross-language translation"),
    SOLVE("Solve", "Structured problem solving")
}
