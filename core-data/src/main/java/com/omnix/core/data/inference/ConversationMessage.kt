package com.omnix.core.data.inference

/**
 * One turn in a conversation, in the format providers universally understand.
 * Kept separate from [com.omnix.core.model.ChatMessage] so the inference layer
 * has no dependency on Room entities or domain persistence models.
 */
data class ConversationMessage(
    val role: ConversationRole,
    val content: String
)

enum class ConversationRole {
    SYSTEM,
    USER,
    ASSISTANT
}
