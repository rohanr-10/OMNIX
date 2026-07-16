package com.omnix.core.data.inference

import com.omnix.core.model.AiMode

/**
 * Knobs passed to the provider for each generation request.
 * All values have sane defaults; callers only override what matters.
 *
 * Providers that don't support a particular parameter ignore it silently.
 */
data class GenerationParameters(
    /** Sampling temperature. 0 = deterministic, 1 = very creative. */
    val temperature: Float = 0.7f,
    /** Nucleus sampling probability threshold. */
    val topP: Float = 0.9f,
    /** Top-k sampling. 0 = disabled. */
    val topK: Int = 40,
    /** Penalise repeated tokens. 1.0 = no penalty. */
    val repeatPenalty: Float = 1.1f,
    /** Hard cap on generated tokens. -1 = provider default. */
    val maxNewTokens: Int = 512,
    /** Stop sequences; generation ends when any sequence is produced. */
    val stopSequences: List<String> = emptyList()
) {
    companion object {
        /** Returns parameters tuned for the given [AiMode]. */
        fun forMode(mode: AiMode): GenerationParameters = when (mode) {
            AiMode.QUICK -> GenerationParameters(
                temperature = 0.5f,
                maxNewTokens = 256
            )
            AiMode.DEEP_THINK, AiMode.DEEP_RESEARCH -> GenerationParameters(
                temperature = 0.4f,
                topP = 0.85f,
                maxNewTokens = 1024
            )
            AiMode.AI_COUNCIL -> GenerationParameters(
                temperature = 0.6f,
                maxNewTokens = 512
            )
            AiMode.CREATIVE -> GenerationParameters(
                temperature = 0.95f,
                topK = 60,
                maxNewTokens = 768
            )
            AiMode.ENGINEER -> GenerationParameters(
                temperature = 0.3f,
                repeatPenalty = 1.05f,
                maxNewTokens = 1024
            )
            AiMode.TRANSLATE -> GenerationParameters(
                temperature = 0.2f,
                maxNewTokens = 512
            )
            else -> GenerationParameters()
        }
    }
}
