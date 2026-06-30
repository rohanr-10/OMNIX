package com.omnix.core.data.council.agents

import com.omnix.core.data.council.CouncilContext
import com.omnix.core.data.council.SimulatedCouncilAgent
import com.omnix.core.model.AgentRole
import javax.inject.Inject

class ResearcherAgent @Inject constructor() : SimulatedCouncilAgent() {

    override val role: AgentRole = AgentRole.RESEARCHER

    override fun thinkingAction(context: CouncilContext) =
        "Gathering relevant facts and context"

    override fun draftingAction(context: CouncilContext) =
        "Assembling supporting evidence"

    override fun buildSummary(context: CouncilContext) =
        "Gathered the relevant factual context and key considerations."

    override fun buildFullText(context: CouncilContext): String {
        return "The relevant background: what's established and well-supported " +
            "about this topic, what's genuinely uncertain or disputed, and which " +
            "specific facts would most change the answer if they turned out to " +
            "be wrong. Flagging anything that needs verification rather than " +
            "presenting it as settled."
    }

    override fun baseConfidence(context: CouncilContext) = 0.78f
}
