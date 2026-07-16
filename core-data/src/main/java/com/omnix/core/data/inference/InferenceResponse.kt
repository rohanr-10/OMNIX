package com.omnix.core.data.inference

/**
 * The completed, immutable response produced after a streaming generation
 * session finishes. Emitted by [InferenceEngine] alongside the token stream
 * so callers can persist metadata (latency, token counts) without reassembling
 * the text from the stream.
 */
data class InferenceResponse(
    val sessionId: String,
    val modelId: String,
    val text: String,
    val promptTokens: Int,
    val generatedTokens: Int,
    val latencyMs: Long,
    val finishReason: FinishReason
)

enum class FinishReason {
    /** Generation completed normally at an EOS token. */
    STOP,
    /** Hit the [GenerationParameters.maxNewTokens] limit. */
    MAX_TOKENS,
    /** Terminated by a stop sequence. */
    STOP_SEQUENCE,
    /** Cancelled by the caller. */
    CANCELLED,
    /** Provider returned an error. */
    ERROR
}
