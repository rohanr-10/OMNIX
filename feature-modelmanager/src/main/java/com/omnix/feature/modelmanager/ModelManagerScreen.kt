@file:OptIn(ExperimentalMaterial3Api::class)

package com.omnix.feature.modelmanager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omnix.core.model.DeviceProfile
import com.omnix.core.model.ModelDownloadState
import com.omnix.core.model.ModelInfo
import com.omnix.core.model.ModelRecommendation
import com.omnix.core.model.ModelTier
import com.omnix.core.ui.components.GlassCard
import com.omnix.core.ui.components.ShimmerSkeleton

@Composable
fun ModelManagerScreen(
    onNavigateBack: () -> Unit,
    viewModel: ModelManagerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.dismissError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Model Manager") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = padding.calculateTopPadding() + 12.dp,
                bottom = padding.calculateBottomPadding() + 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // ── 1. Device Information ──────────────────────────────────────
            item {
                SectionHeader(title = "Device Information", icon = Icons.Filled.Memory)
            }
            item {
                if (uiState.isLoadingDevice) {
                    ShimmerSkeleton(height = 180.dp)
                } else {
                    uiState.deviceProfile?.let { profile ->
                        DeviceInfoCard(profile = profile)
                    }
                }
            }

            // ── 2. Recommended Tier ────────────────────────────────────────
            item { SectionHeader(title = "Recommended Tier", icon = Icons.Filled.Star) }
            item {
                if (uiState.isLoadingDevice) {
                    ShimmerSkeleton(height = 100.dp)
                } else {
                    uiState.recommendation?.let { rec ->
                        RecommendationCard(recommendation = rec)
                    }
                }
            }

            // ── 3. Installed Models ────────────────────────────────────────
            item { SectionHeader(title = "Installed Models", icon = Icons.Filled.CheckCircle) }
            if (uiState.isLoadingModels) {
                items(2) { ShimmerSkeleton(height = 120.dp) }
            } else if (uiState.installedModels.isEmpty()) {
                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "No models installed yet. Download one from the list below.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                items(uiState.installedModels, key = { it.id }) { model ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(tween(300)) + slideInVertically(tween(300)) { it / 2 }
                    ) {
                        InstalledModelCard(
                            model = model,
                            onSelect = { viewModel.selectModel(model.id) },
                            onDelete = { viewModel.deleteModel(model.id) }
                        )
                    }
                }
            }

            // ── 4. Available Models ────────────────────────────────────────
            item { SectionHeader(title = "Available Models", icon = Icons.Filled.Download) }
            if (uiState.isLoadingModels) {
                items(3) { ShimmerSkeleton(height = 130.dp) }
            } else {
                items(uiState.availableModels, key = { it.id }) { model ->
                    AvailableModelCard(
                        model = model,
                        isDownloading = uiState.downloadingModelId == model.id,
                        onDownload = { viewModel.downloadModel(model.id) }
                    )
                }
            }

            // ── 5. Active Model ────────────────────────────────────────────
            uiState.activeModel?.let { active ->
                item { SectionHeader(title = "Active Model", icon = Icons.Filled.PlayArrow) }
                item { ActiveModelCard(model = active) }
            }
        }
    }
}

// ─── Section header ───────────────────────────────────────────────────────────

@Composable
private fun SectionHeader(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ─── 1. Device info ───────────────────────────────────────────────────────────

@Composable
private fun DeviceInfoCard(profile: DeviceProfile) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            DeviceRow("Android Version", profile.androidVersion)
            DeviceRow("Device", "${profile.manufacturer} ${profile.model}")
            DeviceRow("CPU ABI", profile.cpuAbi)
            DeviceRow("CPU Cores", profile.cpuCoreCount.toString())
            DeviceRow(
                "RAM",
                "${profile.totalRamMb / 1024} GB total · ${profile.availableRamMb / 1024} GB available"
            )
            DeviceRow(
                "Storage",
                "${profile.freeStorageMb / 1024} GB free / ${profile.totalStorageMb / 1024} GB"
            )
            DeviceRow("NPU Acceleration", if (profile.hasNpuAcceleration) "Supported" else "Not available")
            DeviceRow("GPU Delegate", if (profile.hasGpuDelegateSupport) "Supported" else "Not available")
        }
    }
}

@Composable
private fun DeviceRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1.4f)
        )
    }
}

// ─── 2. Recommendation ───────────────────────────────────────────────────────

@Composable
private fun RecommendationCard(recommendation: ModelRecommendation) {
    val tierColor = when (recommendation.recommendedTier) {
        ModelTier.LITE -> MaterialTheme.colorScheme.secondary
        ModelTier.STANDARD -> MaterialTheme.colorScheme.primary
        ModelTier.PRO -> MaterialTheme.colorScheme.tertiary
    }

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    color = tierColor.copy(alpha = 0.15f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = recommendation.recommendedTier.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = tierColor,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
                Text(
                    text = "Recommended",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = recommendation.reason,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ─── 3. Installed model card ─────────────────────────────────────────────────

@Composable
private fun InstalledModelCard(
    model: ModelInfo,
    onSelect: () -> Unit,
    onDelete: () -> Unit
) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = model.displayName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        if (model.isActive) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = MaterialTheme.shapes.extraSmall
                            ) {
                                Text(
                                    text = "Active",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    Text(
                        text = "${model.provider.displayName} · ${model.sizeGb} GB · ${model.parameterCount}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (!model.isActive) {
                        IconButton(onClick = onSelect) {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = "Select",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

// ─── 4. Available model card ─────────────────────────────────────────────────

@Composable
private fun AvailableModelCard(
    model: ModelInfo,
    isDownloading: Boolean,
    onDownload: () -> Unit
) {
    val animatedProgress by animateFloatAsState(
        targetValue = model.downloadProgress,
        animationSpec = tween(durationMillis = 200),
        label = "downloadProgress"
    )

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = model.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = model.provider.displayName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        ModelChip("${model.sizeGb} GB")
                        ModelChip("${model.parameterCount} params")
                        ModelChip("≥${model.minRamGb.toInt()} GB RAM")
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                when (model.downloadState) {
                    ModelDownloadState.NOT_DOWNLOADED, ModelDownloadState.FAILED -> {
                        Button(
                            onClick = onDownload,
                            enabled = !isDownloading,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                Icons.Filled.Download,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Download", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                    ModelDownloadState.QUEUED -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            strokeWidth = 3.dp
                        )
                    }
                    ModelDownloadState.DOWNLOADING, ModelDownloadState.VERIFYING -> {
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                progress = { animatedProgress },
                                modifier = Modifier.size(40.dp),
                                strokeWidth = 3.dp,
                                strokeCap = StrokeCap.Round
                            )
                            Text(
                                text = "${(animatedProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    ModelDownloadState.READY -> {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = "Downloaded",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            // Inline linear progress shown during active download.
            if (model.downloadState == ModelDownloadState.DOWNLOADING) {
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.fillMaxWidth(),
                    strokeCap = StrokeCap.Round,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = when (model.downloadState) {
                        ModelDownloadState.VERIFYING -> "Verifying…"
                        else -> "Downloading ${(animatedProgress * model.sizeGb).let { "%.1f".format(it) }} / ${model.sizeGb} GB"
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun ModelChip(label: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
        )
    }
}

// ─── 5. Active model card ─────────────────────────────────────────────────────

@Composable
private fun ActiveModelCard(model: ModelInfo) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = model.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            DeviceRow("Provider", model.provider.displayName)
            DeviceRow("Parameters", model.parameterCount)
            DeviceRow("Context Window", "${model.contextWindowTokens.formatTokens()} tokens")
            DeviceRow("Approx RAM Usage", "~${model.minRamGb} GB")
            DeviceRow("Tier", model.tier.displayName)
        }
    }
}

private fun Int.formatTokens(): String = when {
    this >= 1_000_000 -> "${this / 1_000_000}M"
    this >= 1_000 -> "${this / 1_000}K"
    else -> toString()
}
