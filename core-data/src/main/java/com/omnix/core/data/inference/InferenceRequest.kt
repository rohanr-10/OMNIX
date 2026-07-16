package com.omnix.core.data.inference

import com.omnix.core.model.AiMode

/**
 * Everything a provider needs to generate a response.
 * Created by [InferenceEngine] from the chat inputs and active model;
 * providers never need to query model state themselves.
 */
data class InferenceRequest(
    /** Unique ID linking this request to an [InferenceSession]. */
    val sessionId: String,
    /** ID of the model that should handle this request. */
    val modelId: String,
    /** Full conversation history, system prompt first, trimmed to context window. */
    val messages: List<ConversationMessage>,
    /** Tuned parameters for this request. */
    val parameters: GenerationParameters,
    /** The mode selected by the user — providers may use it for extra behaviour. */
    val mode: AiMode
)
