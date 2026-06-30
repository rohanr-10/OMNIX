package com.omnix.core.model

/**
 * One of the fixed specialist roles that can participate in an AI Council session.
 */
enum class AgentRole(val displayName: String) {
    PLANNER("Planner"),
    ANALYST("Analyst"),
    ENGINEER("Engineer"),
    RESEARCHER("Researcher"),
    CREATIVE("Creative"),
    TEACHER("Teacher"),
    CRITIC("Critic"),
    SKEPTIC("Skeptic"),
    WRITER("Writer"),
    SAFETY("Safety"),
    MODERATOR("Moderator")
}

enum class AgentStatus {
    IDLE,
    THINKING,
    DRAFTING,
    REVISING,
    DONE,
    ERROR
}

/**
 * Live state for a single agent node during an AI Council deliberation.
 * This is what drives the animated node graph UI.
 */
data class AgentNodeState(
    val role: AgentRole,
    val status: AgentStatus = AgentStatus.IDLE,
    val confidence: Float = 0f, // 0f..1f
    val progress: Float = 0f,   // 0f..1f
    val currentAction: String = "",
    val outgoingConnections: List<AgentRole> = emptyList()
)

/**
 * A finalized contribution from one agent, attached to the final message
 * once council deliberation completes.
 */
data class AgentContribution(
    val role: AgentRole,
    val summary: String,
    val confidence: Float,
    val fullText: String
)

/**
 * The full state of an AI Council deliberation for a single user turn.
 */
data class CouncilSession(
    val id: String,
    val messageId: String,
    val nodes: List<AgentNodeState>,
    val isComplete: Boolean = false,
    val consensusText: String? = null,
    val contributions: List<AgentContribution> = emptyList()
)
