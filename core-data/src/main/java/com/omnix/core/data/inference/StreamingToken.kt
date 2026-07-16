package com.omnix.core.data.inference

/**
 * A single unit of streamed output emitted by an [InferenceProvider].
 *
 * [text] is the new text fragment for this token (may be one word-piece,
 * one word, or occasionally multiple words depending on the provider).
 *
 * [accumulatedText] is the full response text assembled so far — consumers
 * that only care about the running total (e.g. [com.omnix.core.data.ai.AiResponseProvider]
 * adapters) can ignore [text] and only observe [accumulatedText].
 *
 * [isFinal] is true on the last emission; the flow completes immediately after.
 */
data class StreamingToken(
    val text: String,
    val accumulatedText: String,
    val isFinal: Boolean = false,
    val tokenIndex: Int = 0
)
