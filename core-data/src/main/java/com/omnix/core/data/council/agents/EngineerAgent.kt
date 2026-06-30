package com.omnix.core.data.council.agents

import com.omnix.core.data.council.CouncilContext
import com.omnix.core.data.council.SimulatedCouncilAgent
import com.omnix.core.model.AgentRole
import javax.inject.Inject

class EngineerAgent @Inject constructor() : SimulatedCouncilAgent() {

    override val role: AgentRole = AgentRole.ENGINEER

    override fun thinkingAction(context: CouncilContext) =
        "Assessing the technical feasibility and implementation path"

    override fun draftingAction(context: CouncilContext) =
        "Drafting a concrete implementation approach"

    override fun buildSummary(context: CouncilContext) =
        "Proposed a concrete, implementable approach."

    override fun buildFullText(context: CouncilContext): String {
        val plan = context.contributionFrom(AgentRole.PLANNER)
        val planRef = if (plan != null) "Building on the Planner's breakdown, " else ""
        return "${planRef}from an implementation standpoint: the core technical " +
            "components, the interfaces between them, and a concrete, buildable " +
            "path from current state to a working result, including the parts " +
            "most likely to need iteration."
    }

    override fun baseConfidence(context: CouncilContext) = 0.85f
}
