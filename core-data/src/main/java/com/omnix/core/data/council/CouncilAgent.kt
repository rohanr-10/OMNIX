package com.omnix.core.data.council

import com.omnix.core.model.AgentContribution
import com.omnix.core.model.AgentRole
import kotlinx.coroutines.flow.Flow

/**
 * One participant in an AI Council deliberation.
 *
 * To add a new specialist (e.g. a future "Translator" or "Historian" agent):
 *  1. Add the role to [AgentRole] in core-model.
 *  2. Implement this interface.
 *  3. Bind it with @IntoSet in [com.omnix.core.data.di.CouncilModule].
 * No other part of the orchestrator, UI, or registry needs to change.
 */
interface CouncilAgent {

    val role: AgentRole

    /**
     * Produces this agent's contribution for the given deliberation context.
     * Implementations stream incremental progress via [CouncilProgressEmitter]
     * so the UI can animate "thinking" -> "drafting" -> "done" in real time,
     * then return the finalized contribution.
     */
    suspend fun deliberate(
        context: CouncilContext,
        progress: CouncilProgressEmitter
    ): AgentContribution
}

/**
 * Snapshot of everything an agent needs to do its job: the original prompt,
 * the mode the user invoked the council under, and whatever contributions
 * earlier phases have already produced (e.g. specialists can see the
 * Planner's breakdown; Critic/Skeptic/Safety can see the specialists' drafts;
 * Moderator sees everything).
 */
data class CouncilContext(
    val prompt: String,
    val conversationHistory: List<String>,
    val priorContributions: List<AgentContribution>
) {
    fun contributionFrom(role: AgentRole): AgentContribution? =
        priorContributions.firstOrNull { it.role == role }
}

/**
 * Callback surface an agent uses to report live progress mid-deliberation.
 * Kept as a simple functional interface (rather than requiring agents to
 * build their own Flow) so individual agent implementations stay simple.
 */
interface CouncilProgressEmitter {
    suspend fun emitThinking(action: String)
    suspend fun emitDrafting(action: String, progress: Float)
    suspend fun emitConfidence(confidence: Float)
}
