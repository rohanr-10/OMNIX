package com.omnix.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omnix.core.model.ChatSession
import com.omnix.core.ui.components.GlassCard
import com.omnix.core.ui.components.ShimmerSkeleton

@Composable
fun HomeScreen(
    onOpenChat: (sessionId: String) -> Unit,
    onOpenModelManager: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.createNewChat(onCreated = onOpenChat) },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("New Chat") }
            )
        }
    ) { padding ->
        HomeContent(
            uiState = uiState,
            onOpenChat = onOpenChat,
            onOpenModelManager = onOpenModelManager,
            contentPadding = padding
        )
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onOpenChat: (sessionId: String) -> Unit,
    onOpenModelManager: () -> Unit,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = contentPadding.calculateTopPadding() + 16.dp,
            bottom = contentPadding.calculateBottomPadding() + 96.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { HeroSection(greeting = uiState.greeting) }

        item {
            QuickActionsRow(
                onOpenModelManager = onOpenModelManager
            )
        }

        item {
            Text(
                text = "Recent Chats",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (uiState.isLoading) {
            items(3) { ShimmerSkeleton(modifier = Modifier.fillMaxWidth()) }
        } else if (uiState.recentSessions.isEmpty()) {
            item {
                Text(
                    text = "No conversations yet. Start a new chat to begin.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(uiState.recentSessions, key = { it.id }) { session ->
                RecentChatCard(session = session, onClick = { onOpenChat(session.id) })
            }
        }

        item {
            Text(
                text = "System",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        item {
            StatusOverviewCard(
                installedModelLabel = uiState.installedModelLabel,
                installedPluginCount = uiState.installedPluginCount,
                storageUsedMb = uiState.storageUsedMb,
                storageTotalMb = uiState.storageTotalMb,
                onOpenModelManager = onOpenModelManager
            )
        }
    }
}

@Composable
private fun HeroSection(greeting: String) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = greeting,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "One Intelligence. Many Minds.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun QuickActionsRow(onOpenModelManager: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        QuickActionChip(
            label = "AI Council",
            icon = Icons.Filled.Groups,
            modifier = Modifier.weight(1f),
            onClick = {}
        )
        QuickActionChip(
            label = "Research",
            icon = Icons.Filled.Search,
            modifier = Modifier.weight(1f),
            onClick = {}
        )
        QuickActionChip(
            label = "Models",
            icon = Icons.Filled.Memory,
            modifier = Modifier.weight(1f),
            onClick = onOpenModelManager
        )
    }
}

@Composable
private fun QuickActionChip(
    label: String,
    icon: ImageVector,
    modifier: Modifier,
    onClick: () -> Unit
) {
    GlassCard(modifier = modifier.clickable { onClick() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun RecentChatCard(session: ChatSession, onClick: () -> Unit) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Chat,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(text = session.title, style = MaterialTheme.typography.titleSmall)
                Text(
                    text = session.lastMessagePreview.ifBlank { "No messages yet" },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun StatusOverviewCard(
    installedModelLabel: String,
    installedPluginCount: Int,
    storageUsedMb: Int,
    storageTotalMb: Int,
    onOpenModelManager: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenModelManager() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Memory,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.height(0.dp).then(Modifier.size(8.dp)))
                Text(
                    text = "Model: $installedModelLabel",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Plugins installed: $installedPluginCount", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Storage: $storageUsedMb MB / $storageTotalMb MB",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tap to manage models →",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
