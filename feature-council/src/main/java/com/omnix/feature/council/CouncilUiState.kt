package com.omnix.feature.council

import com.omnix.core.model.AgentContribution
import com.omnix.core.model.AgentNodeState

data class CouncilUiState(
    val prompt: String = "",
    val isRunning: Boolean = true,
    val isComplete: Boolean = false,
    val nodes: List<AgentNodeState> = emptyList(),
    val consensusText: String? = null,
    val contributions: List<AgentContribution> = emptyList(),
    val selectedRoleForInspection: AgentContribution? = null
)
