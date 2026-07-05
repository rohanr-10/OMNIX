package com.omnix.feature.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton as M3IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omnix.core.model.AiMode
import com.omnix.core.model.ChatMessage
import com.omnix.core.model.MessageRole
import com.omnix.core.model.MessageStatus
import com.omnix.core.ui.components.ShimmerSkeleton
import com.omnix.core.ui.theme.OmnixRadii

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    onNavigateBack: () -> Unit,
    onOpenAiCouncil: (prompt: String) -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.sessionTitle) }
            )
        },
        bottomBar = {
            Column {
                ModeChipsRow(
                    selectedMode = uiState.selectedMode,
                    onModeSelected = viewModel::onModeSelected
                )
                Composer(
                    text = uiState.draftText,
                    enabled = !uiState.isAssistantResponding,
                    onTextChanged = viewModel::onDraftChanged,
                    onSend = viewModel::sendMessage,
                    onCouncil = {
                        viewModel.startCouncilDeliberation(onNavigate = onOpenAiCouncil)
                    }
                )
            }
        }
    ) { padding ->
        if (uiState.isLoadingHistory) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(4) { ShimmerSkeleton() }
            }
        } else {
            MessageList(
                messages = uiState.messages,
                listState = listState,
                contentPadding = padding,
                onRegenerate = viewModel::regenerateLastResponse,
                onDelete = viewModel::deleteMessage
            )
        }
    }
}

@Composable
private fun ModeChipsRow(
    selectedMode: AiMode,
    onModeSelected: (AiMode) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ChatModeChipOrder) { mode ->
            FilterChip(
                selected = mode == selectedMode,
                onClick = { onModeSelected(mode) },
                label = { Text(mode.displayName) }
            )
        }
    }
}

@Composable
private fun Composer(
    text: String,
    enabled: Boolean,
    onTextChanged: (String) -> Unit,
    onSend: () -> Unit,
    onCouncil: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            placeholder = { Text("Ask OMNIX anything…") },
            enabled = enabled,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            shape = OmnixRadii.pill
        )
        M3IconButton(
            onClick = onCouncil,
            enabled = enabled && text.isNotBlank()
        ) {
            Icon(
                imageVector = Icons.Filled.Groups,
                contentDescription = "Convene AI Council"
            )
        }
        M3IconButton(onClick = onSend, enabled = enabled && text.isNotBlank()) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send"
            )
        }
    }
}

@Composable
private fun MessageList(
    messages: List<ChatMessage>,
    listState: androidx.compose.foundation.lazy.LazyListState,
    contentPadding: PaddingValues,
    onRegenerate: () -> Unit,
    onDelete: (String) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = contentPadding.calculateTopPadding() + 8.dp,
            bottom = contentPadding.calculateBottomPadding() + 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(messages, key = { it.id }) { message ->
            MessageBubble(message = message, onRegenerate = onRegenerate, onDelete = onDelete)
        }
    }
}

@Composable
private fun MessageBubble(
    message: ChatMessage,
    onRegenerate: () -> Unit,
    onDelete: (String) -> Unit
) {
    val isUser = message.role == MessageRole.USER
    val bubbleShape = if (isUser) OmnixRadii.chatBubbleUser else OmnixRadii.chatBubbleAssistant
    val containerColor = if (isUser) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (isUser) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = bubbleShape,
            color = containerColor,
            modifier = Modifier.fillMaxWidth(fraction = 0.85f)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.content.ifBlank { "…" },
                    color = contentColor,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (message.status == MessageStatus.STREAMING) {
                    Text(
                        text = "typing…",
                        color = contentColor.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}
