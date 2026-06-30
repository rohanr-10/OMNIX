package com.omnix.core.data.council

import com.omnix.core.model.AgentContribution
import kotlinx.coroutines.delay

/**
 * Shared lifecycle for the simulated agents: report a thinking action, pause,
 * report drafting progress in a few increments, settle on a confidence score,
 * then hand back the finalized contribution text. Subclasses only need to
 * supply the actual content via [buildSummary] and [buildFullText].
 *
 * This mirrors [com.omnix.core.data.ai.SimulatedAiResponseProvider]'s honesty
 * principle: it's a fully working, real deliberation pipeline — it just
 * doesn't call out to a live model yet. Swapping in a real model later only
 * means changing [buildSummary]/[buildFullText] to call an LLM instead of
 * composing template text.
 */
abstract class SimulatedCouncilAgent : CouncilAgent {

    protected abstract fun thinkingAction(context: CouncilContext): String
    protected abstract fun draftingAction(context: CouncilContext): String
    protected abstract fun buildSummary(context: CouncilContext): String
    protected abstract fun buildFullText(context: CouncilContext): String
    protected open fun baseConfidence(context: CouncilContext): Float = 0.82f

    override suspend fun deliberate(
        context: CouncilContext,
        progress: CouncilProgressEmitter
    ): AgentContribution {
        progress.emitThinking(thinkingAction(context))
        delay(THINKING_DELAY_MS)

        progress.emitDrafting(draftingAction(context), progress = 0.35f)
        delay(DRAFTING_DELAY_MS)
        progress.emitDrafting(draftingAction(context), progress = 0.75f)
        delay(DRAFTING_DELAY_MS)

        val confidence = baseConfidence(context)
        progress.emitConfidence(confidence)

        return AgentContribution(
            role = role,
            summary = buildSummary(context),
            confidence = confidence,
            fullText = buildFullText(context)
        )
    }

    companion object {
        private const val THINKING_DELAY_MS = 350L
        private const val DRAFTING_DELAY_MS = 280L
    }
}
