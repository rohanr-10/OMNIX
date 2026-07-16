package com.omnix.core.data.inference

import com.omnix.core.model.AiMode

/**
 * Builds the list of [ConversationMessage]s that goes into an [InferenceRequest].
 *
 * Responsibilities:
 *  - Prepend a system prompt appropriate to the [AiMode].
 *  - Convert raw history strings (user/assistant alternating) into typed messages.
 *  - Trim history from the oldest end when the estimated token count would
 *    exceed [contextWindowTokens], always keeping the system prompt and the
 *    most recent user message intact.
 *
 * Token estimation uses a simple 4-chars-per-token heuristic which is
 * accurate enough for trimming without needing a real tokenizer.
 */
class ConversationContext(
    private val contextWindowTokens: Int = DEFAULT_CONTEXT_TOKENS
) {

    fun build(
        userPrompt: String,
        history: List<String>,
        mode: AiMode
    ): List<ConversationMessage> {
        val system = ConversationMessage(
            role = ConversationRole.SYSTEM,
            content = systemPromptFor(mode)
        )
        val historicMessages = historyToMessages(history)
        val userMessage = ConversationMessage(
            role = ConversationRole.USER,
            content = userPrompt
        )

        // Trim history from oldest first until we're within budget.
        val budget = contextWindowTokens - estimateTokens(system.content) -
            estimateTokens(userMessage.content) - RESPONSE_HEADROOM
        val trimmedHistory = trimToBudget(historicMessages, budget)

        return buildList {
            add(system)
            addAll(trimmedHistory)
            add(userMessage)
        }
    }

    private fun historyToMessages(history: List<String>): List<ConversationMessage> {
        // History arrives as a flat list of content strings in chronological order;
        // even indices are user turns, odd indices are assistant turns.
        return history.mapIndexed { index, content ->
            ConversationMessage(
                role = if (index % 2 == 0) ConversationRole.USER else ConversationRole.ASSISTANT,
                content = content
            )
        }
    }

    private fun trimToBudget(
        messages: List<ConversationMessage>,
        budgetTokens: Int
    ): List<ConversationMessage> {
        if (budgetTokens <= 0) return emptyList()
        val result = ArrayDeque(messages)
        var total = result.sumOf { estimateTokens(it.content) }
        while (total > budgetTokens && result.isNotEmpty()) {
            val removed = result.removeFirst()
            total -= estimateTokens(removed.content)
        }
        return result.toList()
    }

    private fun estimateTokens(text: String): Int = (text.length / CHARS_PER_TOKEN).coerceAtLeast(1)

    private fun systemPromptFor(mode: AiMode): String = when (mode) {
        AiMode.QUICK ->
            "You are OMNIX, a concise AI assistant. Answer clearly and directly."
        AiMode.DEEP_THINK ->
            "You are OMNIX in deep reasoning mode. Think through problems step by step, " +
                "examining assumptions carefully before drawing conclusions."
        AiMode.AI_COUNCIL ->
            "You are one agent in the OMNIX AI Council. Provide your specialist perspective " +
                "clearly and concisely so the Moderator can synthesize a consensus."
        AiMode.DEEP_RESEARCH ->
            "You are OMNIX in research mode. Provide thorough, well-structured answers, " +
                "distinguishing clearly between what is established and what is uncertain."
        AiMode.CREATIVE ->
            "You are OMNIX in creative mode. Be imaginative, original, and expressive."
        AiMode.ENGINEER ->
            "You are OMNIX in engineering mode. Focus on concrete, implementable solutions " +
                "with clear technical reasoning."
        AiMode.EXPLAIN ->
            "You are OMNIX in explanation mode. Break concepts down step by step, " +
                "building from fundamentals to the full picture."
        AiMode.SUMMARIZE ->
            "You are OMNIX in summarization mode. Distil content to its essential points only."
        AiMode.TRANSLATE ->
            "You are OMNIX in translation mode. Translate accurately, preserving tone and meaning."
        AiMode.SOLVE ->
            "You are OMNIX in problem-solving mode. Define the problem precisely, " +
                "work through steps methodically, and verify the result."
        AiMode.RESEARCH ->
            "You are OMNIX in analysis mode. Lay out relevant facts and their relationships " +
                "before drawing conclusions."
    }

    companion object {
        private const val DEFAULT_CONTEXT_TOKENS = 4_096
        private const val CHARS_PER_TOKEN = 4
        private const val RESPONSE_HEADROOM = 512
    }
}
