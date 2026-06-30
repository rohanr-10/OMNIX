package com.omnix.core.data.council.agents

import com.omnix.core.data.council.CouncilContext
import com.omnix.core.data.council.SimulatedCouncilAgent
import com.omnix.core.model.AgentRole
import javax.inject.Inject

class SkepticAgent @Inject constructor() : SimulatedCouncilAgent() {

    override val role: AgentRole = AgentRole.SKEPTIC

    override fun thinkingAction(context: CouncilContext) =
        "Stress-testing the assumptions behind the drafts"

    override fun draftingAction(context: CouncilContext) =
        "Listing the claims most likely to be wrong"

    override fun buildSummary(context: CouncilContext) =
        "Flagged the assumptions most worth double-checking."

    override fun buildFullText(context: CouncilContext): String {
        val researchNote = context.contributionFrom(AgentRole.RESEARCHER)
        val researchRef = if (researchNote != null) {
            "Given the Researcher's noted uncertainties, "
        } else {
            "Without independent verification, "
        }
        return "${researchRef}the council should be careful not to overstate confidence. " +
            "The claims most worth flagging are the ones stated as fact but that actually " +
            "rest on an unstated assumption, and anything that would change materially " +
            "if the situation were slightly different than assumed."
    }

    override fun baseConfidence(context: CouncilContext) = 0.72f
}
