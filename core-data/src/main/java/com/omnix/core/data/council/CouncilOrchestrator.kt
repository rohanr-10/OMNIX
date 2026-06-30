package com.omnix.core.data.council

import com.omnix.core.model.AgentContribution
import com.omnix.core.model.AgentNodeState
import com.omnix.core.model.AgentRole
import com.omnix.core.model.AgentStatus
import com.omnix.core.model.CouncilSession
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Orchestrates a full AI Council deliberation in four phases:
 *
 *  1. Planner scopes the problem alone.
 *  2. Specialists (Engineer, Researcher, Creative, Writer) draft in parallel,
 *     each seeing the Planner's breakdown.
 *  3. Reviewers (Critic, Skeptic, Safety) review the specialists' drafts in
 *     parallel, each seeing every specialist contribution so far.
 *  4. Moderator synthesizes a final consensus from everything.
 *
 * The whole session is emitted as a live [Flow] of [CouncilSession] snapshots
 * so the UI can animate node status/progress/confidence in real time rather
 * than waiting for the entire deliberation to finish.
 */
@Singleton
class CouncilOrchestrator @Inject constructor(
    private val agentRegistry: CouncilAgentRegistry
) {

    fun runCouncil(
        prompt: String,
        conversationHistory: List<String> = emptyList(),
        messageId: String
    ): Flow<CouncilSession> = channelFlow {
        val sessionId = UUID.randomUUID().toString()
        val allRoles = agentRegistry.allRoles()
        val nodeStates = ConcurrentHashMap<AgentRole, AgentNodeState>()
        allRoles.forEach { role ->
            nodeStates[role] = AgentNodeState(role = role, status = AgentStatus.IDLE)
        }
        val contributions = CopyOnWriteArrayList<AgentContribution>()
        val publishMutex = Mutex()

        suspend fun publish(isComplete: Boolean = false, consensusText: String? = null) = publishMutex.withLock {
            send(
                CouncilSession(
                    id = sessionId,
                    messageId = messageId,
                    nodes = allRoles.mapNotNull { nodeStates[it] },
                    isComplete = isComplete,
                    consensusText = consensusText,
                    contributions = contributions.toList()
                )
            )
        }

        fun emitterFor(role: AgentRole, connections: List<AgentRole>) = object : CouncilProgressEmitter {
            override suspend fun emitThinking(action: String) {
                nodeStates[role] = nodeStates[role]!!.copy(
                    status = AgentStatus.THINKING,
                    currentAction = action,
                    progress = 0.1f,
                    outgoingConnections = connections
                )
                publish()
            }

            override suspend fun emitDrafting(action: String, progress: Float) {
                nodeStates[role] = nodeStates[role]!!.copy(
                    status = AgentStatus.DRAFTING,
                    currentAction = action,
                    progress = progress,
                    outgoingConnections = connections
                )
                publish()
            }

            override suspend fun emitConfidence(confidence: Float) {
                nodeStates[role] = nodeStates[role]!!.copy(confidence = confidence)
                publish()
            }
        }

        suspend fun runAgent(role: AgentRole, connections: List<AgentRole>) {
            val agent = agentRegistry.agentFor(role) ?: return
            val context = CouncilContext(
                prompt = prompt,
                conversationHistory = conversationHistory,
                priorContributions = contributions.toList()
            )
            val contribution = agent.deliberate(context, emitterFor(role, connections))
            contributions += contribution
            nodeStates[role] = nodeStates[role]!!.copy(
                status = AgentStatus.DONE,
                progress = 1f,
                confidence = contribution.confidence,
                currentAction = "Done"
            )
            publish()
        }

        publish()

        // Phase 1: Planner alone.
        runAgent(AgentRole.PLANNER, connections = SPECIALIST_ROLES)

        // Phase 2: specialists run concurrently, each fed by the Planner.
        coroutineScope {
            SPECIALIST_ROLES.filter { agentRegistry.agentFor(it) != null }.forEach { role ->
                launch { runAgent(role, connections = REVIEWER_ROLES) }
            }
        }

        // Phase 3: reviewers run concurrently, each fed by every specialist so far.
        coroutineScope {
            REVIEWER_ROLES.filter { agentRegistry.agentFor(it) != null }.forEach { role ->
                launch { runAgent(role, connections = listOf(AgentRole.MODERATOR)) }
            }
        }

        // Phase 4: Moderator synthesizes the final consensus.
        runAgent(AgentRole.MODERATOR, connections = emptyList())

        val consensus = contributions.firstOrNull { it.role == AgentRole.MODERATOR }?.fullText
        publish(isComplete = true, consensusText = consensus)
    }

    companion object {
        private val SPECIALIST_ROLES = listOf(
            AgentRole.ENGINEER,
            AgentRole.RESEARCHER,
            AgentRole.CREATIVE,
            AgentRole.WRITER
        )
        private val REVIEWER_ROLES = listOf(
            AgentRole.CRITIC,
            AgentRole.SKEPTIC,
            AgentRole.SAFETY
        )
    }
}
