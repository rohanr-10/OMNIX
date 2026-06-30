package com.omnix.core.data.council.agents

import com.omnix.core.data.council.CouncilContext
import com.omnix.core.data.council.SimulatedCouncilAgent
import com.omnix.core.model.AgentRole
import javax.inject.Inject

class CriticAgent @Inject constructor() : SimulatedCouncilAgent() {

    override val role: AgentRole = AgentRole.CRITIC

    override fun thinkingAction(context: CouncilContext) =
        "Reviewing the specialists' drafts for gaps or weak reasoning"

    override fun draftingAction(context: CouncilContext) =
        "Compiling review notes"

    override fun buildSummary(context: CouncilContext) =
        "Reviewed the drafts for completeness and identified gaps."

    override fun buildFullText(context: CouncilContext): String {
        val draftCount = context.priorContributions.count {
            it.role in setOf(AgentRole.ENGINEER, AgentRole.RESEARCHER, AgentRole.CREATIVE, AgentRole.WRITER)
        }
        val coverage = if (draftCount >= 3) {
            "The specialists' drafts cover the core ground reasonably well."
        } else {
            "The specialists' drafts are thinner than ideal — some angles may be under-covered."
        }
        return "$coverage Checking specifically: does the answer actually address what " +
            "was asked, are there unsupported leaps in the reasoning, and is anything " +
            "presented with more confidence than the underlying evidence supports."
    }

    override fun baseConfidence(context: CouncilContext) = 0.75f
}
