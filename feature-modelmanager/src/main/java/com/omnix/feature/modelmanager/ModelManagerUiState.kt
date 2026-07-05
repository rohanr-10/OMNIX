package com.omnix.feature.modelmanager

import com.omnix.core.model.DeviceProfile
import com.omnix.core.model.ModelInfo
import com.omnix.core.model.ModelRecommendation

data class ModelManagerUiState(
    val isLoadingDevice: Boolean = true,
    val isLoadingModels: Boolean = true,
    val deviceProfile: DeviceProfile? = null,
    val recommendation: ModelRecommendation? = null,
    val allModels: List<ModelInfo> = emptyList(),
    val activeModel: ModelInfo? = null,
    val downloadingModelId: String? = null,
    val errorMessage: String? = null
) {
    val installedModels: List<ModelInfo>
        get() = allModels.filter {
            it.downloadState == com.omnix.core.model.ModelDownloadState.READY
        }

    val availableModels: List<ModelInfo>
        get() = allModels.filter {
            it.downloadState != com.omnix.core.model.ModelDownloadState.READY
        }
}
