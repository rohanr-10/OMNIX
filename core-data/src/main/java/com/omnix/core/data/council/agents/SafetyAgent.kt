package com.omnix.core.data.council.agents

import com.omnix.core.data.council.CouncilContext
import com.omnix.core.data.council.SimulatedCouncilAgent
import com.omnix.core.model.AgentRole
import javax.inject.Inject

class SafetyAgent @Inject constructor() : SimulatedCouncilAgent() {

    override val role: AgentRole = AgentRole.SAFETY

    override fun thinkingAction(context: CouncilContext) =
        "Checking the drafts for safety or harm considerations"

    override fun draftingAction(context: CouncilContext) =
        "Finalizing the safety review"

    override fun buildSummary(context: CouncilContext) =
        "Reviewed for safety considerations; no blocking issues found."

    override fun buildFullText(context: CouncilContext): String {
        return "From a safety standpoint: this request and the drafted responses don't " +
            "raise concerns around harm, privacy, or misuse. The council can proceed to " +
            "consensus without modification on safety grounds."
    }

    override fun baseConfidence(context: CouncilContext) = 0.95f
}
