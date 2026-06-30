@file:OptIn(ExperimentalMaterial3Api::class)

package com.omnix.feature.council

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omnix.core.model.AgentContribution
import com.omnix.core.model.AgentNodeState
import com.omnix.core.model.AgentRole
import com.omnix.core.model.AgentStatus
import com.omnix.core.ui.components.GlassCard
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CouncilScreen(
    onNavigateBack: () -> Unit,
    viewModel: CouncilViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("AI Council") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            NodeGraph(
                nodes = uiState.nodes,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onNodeTapped = { role ->
                    val contribution = uiState.contributions.firstOrNull { it.role == role }
                    viewModel.inspectNode(contribution)
                }
            )

            AnimatedVisibility(visible = uiState.isComplete && uiState.consensusText != null) {
                ConsensusCard(text = uiState.consensusText.orEmpty())
            }
        }
    }

    uiState.selectedRoleForInspection?.let { contribution ->
        InspectionDialog(contribution = contribution, onDismiss = { viewModel.inspectNode(null) })
    }
}

@Composable
private fun NodeGraph(
    nodes: List<AgentNodeState>,
    modifier: Modifier = Modifier,
    onNodeTapped: (AgentRole) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val radius = (minOf(maxWidth, maxHeight) / 2.4f)
        val centerX = maxWidth / 2
        val centerY = maxHeight / 2
        val count = nodes.size.coerceAtLeast(1)

        val positions: Map<AgentRole, Offset> = nodes.mapIndexed { index, node ->
            val angle = (2 * Math.PI * index / count) - (Math.PI / 2)
            val x = centerX + radius * cos(angle).toFloat()
            val y = centerY + radius * sin(angle).toFloat()
            node.role to Offset(x.value, y.value)
        }.toMap()

        ConnectionLines(nodes = nodes, positions = positions)

        nodes.forEach { node ->
            val offset = positions[node.role] ?: Offset.Zero
            AgentNodeBubble(
                node = node,
                modifier = Modifier
                    .offset(
                        x = Dp(offset.x) - NODE_SIZE / 2,
                        y = Dp(offset.y) - NODE_SIZE / 2
                    )
                    .size(NODE_SIZE),
                onTapped = { onNodeTapped(node.role) }
            )
        }
    }
}

@Composable
private fun ConnectionLines(
    nodes: List<AgentNodeState>,
    positions: Map<AgentRole, Offset>
) {
    val lineColor = MaterialTheme.colorScheme.primary
    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
        nodes.forEach { node ->
            val from = positions[node.role] ?: return@forEach
            node.outgoingConnections.forEach { targetRole ->
                val to = positions[targetRole] ?: return@forEach
                val alpha = when (node.status) {
                    AgentStatus.DONE -> 0.55f
                    AgentStatus.THINKING, AgentStatus.DRAFTING -> 0.35f
                    else -> 0.12f
                }
                drawLine(
                    color = lineColor.copy(alpha = alpha),
                    start = androidx.compose.ui.geometry.Offset(from.x * density, from.y * density),
                    end = androidx.compose.ui.geometry.Offset(to.x * density, to.y * density),
                    strokeWidth = 2f * density,
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            }
        }
    }
}

@Composable
private fun AgentNodeBubble(
    node: AgentNodeState,
    modifier: Modifier = Modifier,
    onTapped: () -> Unit
) {
    val animatedProgress by animateFloatAsState(
        targetValue = node.progress,
        animationSpec = tween(durationMillis = 300),
        label = "nodeProgress"
    )

    val statusColor = when (node.status) {
        AgentStatus.IDLE -> MaterialTheme.colorScheme.surfaceVariant
        AgentStatus.THINKING -> MaterialTheme.colorScheme.tertiary
        AgentStatus.DRAFTING -> MaterialTheme.colorScheme.primary
        AgentStatus.REVISING -> MaterialTheme.colorScheme.primary
        AgentStatus.DONE -> MaterialTheme.colorScheme.primary
        AgentStatus.ERROR -> MaterialTheme.colorScheme.error
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxSize(),
            color = statusColor,
            trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
            strokeWidth = 3.dp
        )
        Surface(
            modifier = Modifier
                .size(NODE_SIZE - 12.dp)
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.dp, statusColor.copy(alpha = 0.5f)),
            onClick = onTapped
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = node.role.displayName.take(1),
                    style = MaterialTheme.typography.titleMedium,
                    color = statusColor
                )
                Text(
                    text = node.role.displayName,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun ConsensusCard(text: String) {
    GlassCard(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Consensus", style = MaterialTheme.typography.titleMedium)
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun InspectionDialog(contribution: AgentContribution, onDismiss: () -> Unit) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = contribution.role.displayName,
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Filled.Close, contentDescription = "Close")
                    }
                }
                Text(
                    text = "Confidence: ${(contribution.confidence * 100).toInt()}%",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = contribution.fullText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}

private val NODE_SIZE = 72.dp