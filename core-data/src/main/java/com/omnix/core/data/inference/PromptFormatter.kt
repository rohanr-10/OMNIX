package com.omnix.core.data.inference

/**
 * Converts a list of [ConversationMessage]s into the raw prompt string each
 * provider variant expects. Different model families use different chat
 * templates; the formatter is stateless and provider-specific implementations
 * can be swapped in per-provider without touching anything else.
 */
object PromptFormatter {

    /**
     * Generic ChatML format used by Mistral, Qwen, Phi, and others.
     * ```
     * <|im_start|>system
     * {system}<|im_end|>
     * <|im_start|>user
     * {user}<|im_end|>
     * <|im_start|>assistant
     * ```
     */
    fun toChatML(messages: List<ConversationMessage>): String = buildString {
        messages.forEach { msg ->
            val role = when (msg.role) {
                ConversationRole.SYSTEM -> "system"
                ConversationRole.USER -> "user"
                ConversationRole.ASSISTANT -> "assistant"
            }
            append("<|im_start|>$role\n")
            append(msg.content)
            append("<|im_end|>\n")
        }
        append("<|im_start|>assistant\n")
    }

    /**
     * Llama 3 instruct format used by Meta's Llama 3 family.
     * ```
     * <|begin_of_text|><|start_header_id|>system<|end_header_id|>
     * {system}<|eot_id|>...
     * <|start_header_id|>assistant<|end_header_id|>
     * ```
     */
    fun toLlama3(messages: List<ConversationMessage>): String = buildString {
        append("<|begin_of_text|>")
        messages.forEach { msg ->
            val role = when (msg.role) {
                ConversationRole.SYSTEM -> "system"
                ConversationRole.USER -> "user"
                ConversationRole.ASSISTANT -> "assistant"
            }
            append("<|start_header_id|>$role<|end_header_id|>\n\n")
            append(msg.content.trimEnd())
            append("<|eot_id|>")
        }
        append("<|start_header_id|>assistant<|end_header_id|>\n\n")
    }

    /**
     * Plain text fallback — useful for testing and for providers that handle
     * their own templating internally (e.g. MediaPipe LLM API).
     */
    fun toPlainText(messages: List<ConversationMessage>): String = buildString {
        messages.forEach { msg ->
            when (msg.role) {
                ConversationRole.SYSTEM -> { /* system prompt omitted in plain-text fallback */ }
                ConversationRole.USER -> append("User: ${msg.content}\n")
                ConversationRole.ASSISTANT -> append("Assistant: ${msg.content}\n")
            }
        }
        append("Assistant: ")
    }
}
