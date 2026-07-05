package com.omnix.core.data.repository

import com.omnix.core.data.device.DeviceProfiler
import com.omnix.core.data.di.IoDispatcher
import com.omnix.core.data.local.dao.ModelStateDao
import com.omnix.core.data.local.entity.ModelStateEntity
import com.omnix.core.data.model.ModelCatalog
import com.omnix.core.model.DeviceProfile
import com.omnix.core.model.ModelDownloadState
import com.omnix.core.model.ModelInfo
import com.omnix.core.model.ModelRecommendation
import com.omnix.core.model.ModelTier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelRepositoryImpl @Inject constructor(
    private val modelStateDao: ModelStateDao,
    private val deviceProfiler: DeviceProfiler,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ModelRepository {

    override fun observeAllModels(): Flow<List<ModelInfo>> =
        modelStateDao.observeAll().map { states ->
            val stateMap = states.associateBy { it.modelId }
            ModelCatalog.all.map { info ->
                val state = stateMap[info.id]
                info.copy(
                    downloadState = state?.downloadState
                        ?.let { ModelDownloadState.valueOf(it) }
                        ?: ModelDownloadState.NOT_DOWNLOADED,
                    downloadProgress = state?.downloadProgress ?: 0f,
                    isActive = state?.isActive ?: false
                )
            }
        }

    override fun observeInstalledModels(): Flow<List<ModelInfo>> =
        observeAllModels().map { models ->
            models.filter { it.downloadState == ModelDownloadState.READY }
        }

    override fun observeActiveModel(): Flow<ModelInfo?> =
        observeAllModels().map { models -> models.firstOrNull { it.isActive } }

    override suspend fun getDeviceProfile(): DeviceProfile = withContext(ioDispatcher) {
        deviceProfiler.collect()
    }

    override suspend fun getRecommendation(): ModelRecommendation = withContext(ioDispatcher) {
        val profile = deviceProfiler.collect()
        val ramGb = profile.totalRamMb / 1024f
        when {
            ramGb >= 10f -> ModelRecommendation(
                recommendedTier = ModelTier.PRO,
                reason = "Your device has ${ramGb.toInt()} GB RAM — capable of running Pro-tier models with full context.",
                eligibleTiers = ModelTier.entries
            )
            ramGb >= 6f -> ModelRecommendation(
                recommendedTier = ModelTier.STANDARD,
                reason = "Your device has ${ramGb.toInt()} GB RAM — well suited to Standard-tier models.",
                eligibleTiers = listOf(ModelTier.LITE, ModelTier.STANDARD)
            )
            else -> ModelRecommendation(
                recommendedTier = ModelTier.LITE,
                reason = "Your device has ${ramGb.toInt()} GB RAM — Lite-tier models run reliably within this budget.",
                eligibleTiers = listOf(ModelTier.LITE)
            )
        }
    }

    override suspend fun downloadModel(modelId: String) = withContext(ioDispatcher) {
        // Mark as QUEUED immediately so UI reacts.
        modelStateDao.upsert(
            ModelStateEntity(
                modelId = modelId,
                downloadState = ModelDownloadState.QUEUED.name,
                downloadProgress = 0f,
                isActive = false
            )
        )
        delay(400L)

        // Stream simulated download progress in 5% increments.
        var progress = 0f
        while (progress < 1f) {
            progress = (progress + 0.05f).coerceAtMost(1f)
            modelStateDao.upsert(
                ModelStateEntity(
                    modelId = modelId,
                    downloadState = ModelDownloadState.DOWNLOADING.name,
                    downloadProgress = progress,
                    isActive = false
                )
            )
            delay(120L)
        }

        // Verify phase.
        modelStateDao.upsert(
            ModelStateEntity(
                modelId = modelId,
                downloadState = ModelDownloadState.VERIFYING.name,
                downloadProgress = 1f,
                isActive = false
            )
        )
        delay(600L)

        // Mark READY.
        modelStateDao.upsert(
            ModelStateEntity(
                modelId = modelId,
                downloadState = ModelDownloadState.READY.name,
                downloadProgress = 1f,
                isActive = false
            )
        )
    }

    override suspend fun deleteModel(modelId: String) = withContext(ioDispatcher) {
        modelStateDao.delete(modelId)
    }

    override suspend fun selectModel(modelId: String) = withContext(ioDispatcher) {
        modelStateDao.clearActive()
        // Ensure a READY record exists before activating (idempotent upsert).
        val existing = modelStateDao.getById(modelId)
        if (existing == null) {
            modelStateDao.upsert(
                ModelStateEntity(
                    modelId = modelId,
                    downloadState = ModelDownloadState.READY.name,
                    downloadProgress = 1f,
                    isActive = false
                )
            )
        }
        modelStateDao.setActive(modelId)
    }
}
