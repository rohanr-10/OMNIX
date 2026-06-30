package com.omnix.core.data.council.agents

import com.omnix.core.data.council.CouncilContext
import com.omnix.core.data.council.SimulatedCouncilAgent
import com.omnix.core.model.AgentRole
import javax.inject.Inject

/**
 * Always runs first. Breaks the user's prompt into the sub-questions the
 * rest of the council should address, so specialists aren't all guessing
 * at scope independently.
 */
class PlannerAgent @Inject constructor() : SimulatedCouncilAgent() {

    override val role: AgentRole = AgentRole.PLANNER

    override fun thinkingAction(context: CouncilContext) =
        "Reading the request and identifying its key sub-questions"

    override fun draftingAction(context: CouncilContext) =
        "Drafting a breakdown for the rest of the council"

    override fun buildSummary(context: CouncilContext) =
        "Scoped the problem into concrete sub-tasks for the specialists."

    override fun buildFullText(context: CouncilContext): String {
        val prompt = context.prompt.trim().ifEmpty { "the request" }
        return "Breaking down \"$prompt\": (1) what is actually being asked, " +
            "(2) what constraints or assumptions matter, (3) what a complete " +
            "answer needs to cover, and (4) which specialists are best placed " +
            "to address each part. Engineer and Researcher should focus on the " +
            "factual/technical core; Creative and Writer should focus on framing " +
            "and delivery; Critic, Skeptic, and Safety should review before the " +
            "Moderator finalizes."
    }

    override fun baseConfidence(context: CouncilContext) = 0.9f
}
