package com.omnix.core.data.council.agents

import com.omnix.core.data.council.CouncilContext
import com.omnix.core.data.council.SimulatedCouncilAgent
import com.omnix.core.model.AgentRole
import javax.inject.Inject

class WriterAgent @Inject constructor() : SimulatedCouncilAgent() {

    override val role: AgentRole = AgentRole.WRITER

    override fun thinkingAction(context: CouncilContext) =
        "Considering how the answer should be structured and phrased"

    override fun draftingAction(context: CouncilContext) =
        "Drafting clear, well-organized prose"

    override fun buildSummary(context: CouncilContext) =
        "Shaped the delivery for clarity and readability."

    override fun buildFullText(context: CouncilContext): String {
        return "On delivery: the answer should lead with the most useful point, " +
            "avoid burying the conclusion under preamble, and use plain language " +
            "over jargon wherever the underlying idea allows it. Structure matters " +
            "as much as content here."
    }

    override fun baseConfidence(context: CouncilContext) = 0.8f
}
