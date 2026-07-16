package com.omnix.core.data.inference.providers

import com.omnix.core.data.inference.InferenceProvider
import com.omnix.core.data.inference.InferenceRequest
import com.omnix.core.data.inference.StreamingToken
import com.omnix.core.model.AiMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simulated provider that generates realistic, mode-aware streaming responses
 * without calling a real model backend. This is the active binding for Phase 5;
 * future phases swap it out for a real JNI or HTTP provider by changing a single
 * Hilt binding — nothing else changes.
 *
 * Replaces [com.omnix.core.data.ai.SimulatedAiResponseProvider] as the live
 * implementation. The old class is retained as a reference but no longer bound.
 */
@Singleton
class SimulatedInferenceProvider @Inject constructor() : InferenceProvider {

    override val providerId: String = "simulated"
    override val displayName: String = "Simulated (Phase 5)"

    override suspend fun isAvailable(): Boolean = true

    override fun streamTokens(request: InferenceRequest): Flow<StreamingToken> = flow {
        val fullText = buildResponse(request)
        val words = fullText.split(" ")
        val delayMs = delayFor(request.mode)
        val accumulator = StringBuilder()

        words.forEachIndexed { index, word ->
            if (index > 0) accumulator.append(' ')
            accumulator.append(word)
            val isLast = index == words.lastIndex
            emit(
                StreamingToken(
                    text = if (index == 0) word else " $word",
                    accumulatedText = accumulator.toString(),
                    isFinal = isLast,
                    tokenIndex = index
                )
            )
            if (!isLast) delay(delayMs)
        }
    }

    override suspend fun cancel(sessionId: String) {
        // No native resources to release in the simulated provider.
    }

    private fun delayFor(mode: AiMode): Long = when (mode) {
        AiMode.QUICK -> 16L
        AiMode.DEEP_THINK, AiMode.DEEP_RESEARCH -> 42L
        AiMode.AI_COUNCIL -> 38L
        AiMode.CREATIVE -> 22L
        AiMode.ENGINEER -> 20L
        else -> 26L
    }

    private fun buildResponse(request: InferenceRequest): String {
        val prompt = request.messages.lastOrNull { it.role.name == "USER" }
            ?.content?.trim().orEmpty().ifEmpty { "your request" }
        val modelId = request.modelId.ifBlank { "the active model" }
        return when (request.mode) {
            AiMode.QUICK ->
                "[$modelId] Quick answer on \"$prompt\": the core point addressed directly so you can move on fast."
            AiMode.DEEP_THINK ->
                "[$modelId] Deep reasoning on \"$prompt\": unpacking the underlying assumptions, " +
                    "examining them one by one, then building toward a well-supported conclusion."
            AiMode.AI_COUNCIL ->
                "[$modelId] Council deliberation on \"$prompt\": Planner scoped the problem, " +
                    "specialists drafted their contributions, reviewers stress-tested the logic, " +
                    "and the Moderator is now synthesizing the final consensus."
            AiMode.DEEP_RESEARCH ->
                "[$modelId] Deep research on \"$prompt\": gathering relevant context, " +
                    "cross-checking key claims, and assembling a structured, well-sourced answer."
            AiMode.CREATIVE ->
                "[$modelId] Creative take on \"$prompt\": an original angle that leans " +
                    "into vivid detail and an unexpected framing."
            AiMode.ENGINEER ->
                "[$modelId] Engineering perspective on \"$prompt\": core components, the interfaces " +
                    "between them, and a concrete buildable path from current state to working result."
            AiMode.RESEARCH ->
                "[$modelId] Analysis of \"$prompt\": laying out the relevant facts and their " +
                    "relationships before drawing a conclusion."
            AiMode.EXPLAIN ->
                "[$modelId] Explanation of \"$prompt\" step by step: starting from the basics " +
                    "and building up to the full picture."
            AiMode.SUMMARIZE ->
                "[$modelId] Summary of \"$prompt\": distilled to the essential points only."
            AiMode.TRANSLATE ->
                "[$modelId] Translation of \"$prompt\" preserving tone and meaning as closely as possible."
            AiMode.SOLVE ->
                "[$modelId] Solving \"$prompt\": defining the problem precisely, working through " +
                    "the steps, and verifying the result."
        }
    }
}
