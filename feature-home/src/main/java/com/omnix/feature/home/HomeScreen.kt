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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omnix.core.model.ChatSession
import com.omnix.core.ui.components.GlassCard
import com.omnix.core.ui.components.ShimmerSkeleton

@Composable
fun HomeScreen(
    onOpenChat: (sessionId: String) -> Unit,
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
            contentPadding = padding
        )
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onOpenChat: (sessionId: String) -> Unit,
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

        item { QuickActionsRow() }

        item {
            Text(
                text = "Recent Chats",
                style = MaterialTheme.typography.titleLarge
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
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            StatusOverviewCard(
                installedModelLabel = uiState.installedModelLabel,
                installedPluginCount = uiState.installedPluginCount,
                storageUsedMb = uiState.storageUsedMb,
                storageTotalMb = uiState.storageTotalMb
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
                style = MaterialTheme.typography.headlineSmall
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
private fun QuickActionsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickActionChip(label = "AI Council", modifier = Modifier)
        QuickActionChip(label = "Deep Research", modifier = Modifier)
        QuickActionChip(label = "Documents", modifier = Modifier)
    }
}

@Composable
private fun QuickActionChip(label: String, modifier: Modifier) {
    GlassCard(modifier = modifier) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
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
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
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
    storageTotalMb: Int
) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Model: $installedModelLabel", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Plugins installed: $installedPluginCount", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Storage: $storageUsedMb MB / $storageTotalMb MB",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
