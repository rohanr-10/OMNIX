package com.omnix.core.model

// ─── Device ──────────────────────────────────────────────────────────────────

data class DeviceProfile(
    val androidVersion: String,
    val manufacturer: String,
    val model: String,
    val cpuAbi: String,
    val cpuCoreCount: Int,
    val totalRamMb: Long,
    val availableRamMb: Long,
    val totalStorageMb: Long,
    val freeStorageMb: Long,
    val hasNpuAcceleration: Boolean,
    val hasGpuDelegateSupport: Boolean
)

// ─── Tier ────────────────────────────────────────────────────────────────────

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

// ─── Provider ────────────────────────────────────────────────────────────────

enum class ModelProvider(val displayName: String) {
    GOOGLE("Google"),
    ALIBABA("Alibaba"),
    MICROSOFT("Microsoft"),
    META("Meta"),
    MISTRAL("Mistral AI"),
    DEEPSEEK("DeepSeek")
}

// ─── Download ────────────────────────────────────────────────────────────────

enum class ModelDownloadState {
    NOT_DOWNLOADED,
    QUEUED,
    DOWNLOADING,
    VERIFYING,
    READY,
    FAILED
}

// ─── Model catalog entry ──────────────────────────────────────────────────────

data class ModelInfo(
    val id: String,
    val displayName: String,
    val provider: ModelProvider,
    val tier: ModelTier,
    val sizeGb: Float,
    val parameterCount: String,        // e.g. "3B", "7B"
    val contextWindowTokens: Int,
    val minRamGb: Float,
    val downloadState: ModelDownloadState = ModelDownloadState.NOT_DOWNLOADED,
    val downloadProgress: Float = 0f,  // 0f..1f during DOWNLOADING
    val isActive: Boolean = false
)

// ─── InstalledModel (kept for compatibility with existing references) ─────────

data class InstalledModel(
    val tier: ModelTier,
    val versionTag: String,
    val downloadState: ModelDownloadState,
    val downloadProgress: Float = 0f,
    val sizeOnDiskMb: Int = 0
)
