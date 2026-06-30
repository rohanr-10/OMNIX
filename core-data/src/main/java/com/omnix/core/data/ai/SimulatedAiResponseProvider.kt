package com.omnix.core.data.ai

import com.omnix.core.model.AiMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Deterministic, fully functional placeholder backend: it really does
 * stream real text token-by-token and really does vary behavior per mode,
 * it just doesn't call out to an actual language model yet. This keeps the
 * entire chat pipeline (composer -> repository -> streaming UI -> Room
 * persistence) genuinely exercised while later phases add the real model
 * runner behind this same interface.
 */
@Singleton
class SimulatedAiResponseProvider @Inject constructor() : AiResponseProvider {

    override fun streamResponse(
        prompt: String,
        mode: AiMode,
        history: List<String>
    ): Flow<String> = flow {
        val responseText = buildResponseFor(prompt, mode)
        val words = responseText.split(" ")
        val builder = StringBuilder()

        for ((index, word) in words.withIndex()) {
            if (index > 0) builder.append(' ')
            builder.append(word)
            emit(builder.toString())
            delay(streamDelayMillisFor(mode))
        }
    }

    private fun streamDelayMillisFor(mode: AiMode): Long = when (mode) {
        AiMode.QUICK -> 18L
        AiMode.DEEP_THINK, AiMode.DEEP_RESEARCH, AiMode.AI_COUNCIL -> 45L
        else -> 28L
    }

    private fun buildResponseFor(prompt: String, mode: AiMode): String {
        val trimmedPrompt = prompt.trim().ifEmpty { "your request" }
        return when (mode) {
            AiMode.QUICK ->
                "Here's a quick take on $trimmedPrompt: the short answer covers the core point " +
                    "directly so you can move on fast."
            AiMode.DEEP_THINK ->
                "Thinking through $trimmedPrompt carefully. Breaking it into its underlying " +
                    "assumptions first, then reasoning step by step toward a well-supported conclusion."
            AiMode.AI_COUNCIL ->
                "Convening the council on $trimmedPrompt. Planner is scoping the problem, " +
                    "Analyst and Researcher are gathering supporting detail, Critic and Skeptic are " +
                    "stress-testing the draft, and the Moderator will synthesize a final consensus."
            AiMode.DEEP_RESEARCH ->
                "Starting deep research on $trimmedPrompt. Gathering relevant context, cross " +
                    "checking key claims, and assembling a structured, well-sourced answer."
            AiMode.CREATIVE ->
                "Let's get imaginative with $trimmedPrompt. Here is an original take that leans " +
                    "into vivid detail and an unexpected angle."
            AiMode.ENGINEER ->
                "Approaching $trimmedPrompt from an implementation angle: identifying the core " +
                    "components, the interfaces between them, and a concrete plan to build it correctly."
            AiMode.RESEARCH ->
                "Analyzing $trimmedPrompt. Laying out the relevant facts and how they relate to " +
                    "each other before drawing a conclusion."
            AiMode.EXPLAIN ->
                "Explaining $trimmedPrompt step by step, starting from the basics and building up " +
                    "to the full picture."
            AiMode.SUMMARIZE ->
                "Summarizing $trimmedPrompt into its essential points, trimmed down to what " +
                    "matters most."
            AiMode.TRANSLATE ->
                "Translating $trimmedPrompt while preserving tone and meaning as closely as possible."
            AiMode.SOLVE ->
                "Solving $trimmedPrompt methodically: defining the problem precisely, working " +
                    "through the steps, and checking the result."
        }
    }
}
