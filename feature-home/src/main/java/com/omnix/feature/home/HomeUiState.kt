package com.omnix.feature.home

import com.omnix.core.model.ChatSession

data class HomeUiState(
    val isLoading: Boolean = true,
    val recentSessions: List<ChatSession> = emptyList(),
    val storageUsedMb: Int = 0,
    val storageTotalMb: Int = 0,
    val installedModelLabel: String = "No model installed",
    val installedPluginCount: Int = 0,
    val greeting: String = "Welcome back"
)
