package com.omnix.core.model

/**
 * On-device hardware profile collected at first launch to recommend a model tier.
 */
data class DeviceProfile(
    val totalRamMb: Long,
    val availableRamMb: Long,
    val cpuCoreCount: Int,
    val freeStorageMb: Long,
    val hasNpuAcceleration: Boolean,
    val hasGpuDelegateSupport: Boolean
)

enum class ModelTier(val displayName: String, val approxSizeMb: Int, val minRamMb: Long) {
    LITE("Lite", 600, 3_000),
    STANDARD("Standard", 2_200, 6_000),
    PRO("Pro", 5_400, 10_000)
}

data class ModelRecommendation(
    val recommendedTier: ModelTier,
    val reason: String,
    val eligibleTiers: List<ModelTier>
)

enum class ModelDownloadState {
    NOT_DOWNLOADED,
    QUEUED,
    DOWNLOADING,
    VERIFYING,
    READY,
    FAILED
}

data class InstalledModel(
    val tier: ModelTier,
    val versionTag: String,
    val downloadState: ModelDownloadState,
    val downloadProgress: Float = 0f,
    val sizeOnDiskMb: Int = 0
)
