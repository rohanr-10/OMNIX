package com.omnix.core.data.council

import com.omnix.core.model.AgentRole
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Resolves [AgentRole] -> [CouncilAgent]. Backed by a Hilt multibinding
 * `Set<CouncilAgent>` (see `CouncilModule` in core-data/di), so adding a new
 * agent to the council is purely additive: implement [CouncilAgent], bind it
 * with `@IntoSet`, done. No changes needed here or in [CouncilOrchestrator].
 */
@Singleton
class CouncilAgentRegistry @Inject constructor(
    agents: Set<@JvmSuppressWildcards CouncilAgent>
) {
    private val agentsByRole: Map<AgentRole, CouncilAgent> = agents.associateBy { it.role }

    fun agentFor(role: AgentRole): CouncilAgent? = agentsByRole[role]

    /**
     * Returns roles in a stable, sensible display order (phase order) rather
     * than arbitrary set iteration order, so the node graph UI lays out
     * consistently across runs.
     */
    fun allRoles(): List<AgentRole> = DISPLAY_ORDER.filter { agentsByRole.containsKey(it) }

    companion object {
        private val DISPLAY_ORDER = listOf(
            AgentRole.PLANNER,
            AgentRole.ENGINEER,
            AgentRole.RESEARCHER,
            AgentRole.CREATIVE,
            AgentRole.WRITER,
            AgentRole.CRITIC,
            AgentRole.SKEPTIC,
            AgentRole.SAFETY,
            AgentRole.MODERATOR
        )
    }
}
