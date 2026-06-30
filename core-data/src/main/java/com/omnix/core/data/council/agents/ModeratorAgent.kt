package com.omnix.core.data.council.agents

import com.omnix.core.data.council.CouncilContext
import com.omnix.core.data.council.SimulatedCouncilAgent
import com.omnix.core.model.AgentRole
import javax.inject.Inject

/**
 * Always runs last. Sees every other agent's contribution and produces the
 * single consensus answer that gets shown to the user.
 */
class ModeratorAgent @Inject constructor() : SimulatedCouncilAgent() {

    override val role: AgentRole = AgentRole.MODERATOR

    override fun thinkingAction(context: CouncilContext) =
        "Weighing all council contributions"

    override fun draftingAction(context: CouncilContext) =
        "Drafting the consensus answer"

    override fun buildSummary(context: CouncilContext) =
        "Synthesized a consensus answer from all council contributions."

    override fun buildFullText(context: CouncilContext): String {
        val byRole = context.priorContributions.associateBy { it.role }
        val engineerNote = byRole[AgentRole.ENGINEER]
        val researcherNote = byRole[AgentRole.RESEARCHER]
        val creativeNote = byRole[AgentRole.CREATIVE]
        val skepticNote = byRole[AgentRole.SKEPTIC]
        val safetyNote = byRole[AgentRole.SAFETY]

        val prompt = context.prompt.trim().ifEmpty { "your request" }
        val parts = mutableListOf<String>()
        parts += "Consensus on $prompt:"
        if (engineerNote != null) parts += "On feasibility, the council agrees with the Engineer's approach."
        if (researcherNote != null) parts += "The factual grounding the Researcher laid out holds up."
        if (creativeNote != null) parts += "The Creative agent's reframing is worth keeping in mind alongside the literal answer."
        if (skepticNote != null) parts += "The Skeptic's caveats are noted — treat the most assumption-dependent claims with appropriate caution."
        if (safetyNote != null) parts += "No safety concerns block this answer."
        parts += "Taken together, this is the council's best combined answer, balancing technical accuracy, completeness, and appropriate caution."

        return parts.joinToString(" ")
    }

    override fun baseConfidence(context: CouncilContext): Float {
        if (context.priorContributions.isEmpty()) return 0.5f
        val average = context.priorContributions.map { it.confidence }.average().toFloat()
        return average.coerceIn(0f, 1f)
    }
}
