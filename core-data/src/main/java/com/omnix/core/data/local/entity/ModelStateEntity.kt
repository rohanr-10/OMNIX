package com.omnix.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "model_states")
data class ModelStateEntity(
    @PrimaryKey val modelId: String,
    val downloadState: String,          // ModelDownloadState.name
    val downloadProgress: Float,
    val isActive: Boolean
)
