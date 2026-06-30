package com.omnix.core.data.council.agents

import com.omnix.core.data.council.CouncilContext
import com.omnix.core.data.council.SimulatedCouncilAgent
import com.omnix.core.model.AgentRole
import javax.inject.Inject

class CreativeAgent @Inject constructor() : SimulatedCouncilAgent() {

    override val role: AgentRole = AgentRole.CREATIVE

    override fun thinkingAction(context: CouncilContext) =
        "Looking for an original angle or framing"

    override fun draftingAction(context: CouncilContext) =
        "Sketching an imaginative take"

    override fun buildSummary(context: CouncilContext) =
        "Offered an original angle the literal reading might miss."

    override fun buildFullText(context: CouncilContext): String {
        return "An alternative way to frame this: rather than answering only " +
            "the literal question, consider the underlying need it's pointing " +
            "at, and an unexpected but genuinely useful angle that a purely " +
            "technical answer would likely miss."
    }

    override fun baseConfidence(context: CouncilContext) = 0.7f
}
