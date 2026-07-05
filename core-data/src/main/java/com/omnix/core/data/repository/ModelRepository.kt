package com.omnix.core.data.repository

import com.omnix.core.model.DeviceProfile
import com.omnix.core.model.ModelInfo
import com.omnix.core.model.ModelRecommendation
import kotlinx.coroutines.flow.Flow

interface ModelRepository {

    /** Live list of all catalog models with current download/active state merged in. */
    fun observeAllModels(): Flow<List<ModelInfo>>

    /** Live subset of models that have been fully downloaded. */
    fun observeInstalledModels(): Flow<List<ModelInfo>>

    /** The currently selected active model, or null if none set. */
    fun observeActiveModel(): Flow<ModelInfo?>

    /** Real hardware snapshot from the device. */
    suspend fun getDeviceProfile(): DeviceProfile

    /** RAM-based tier recommendation. */
    suspend fun getRecommendation(): ModelRecommendation

    /**
     * Simulates a model download: emits progress increments over time,
     * then marks the model READY in the database.
     */
    suspend fun downloadModel(modelId: String)

    /** Removes a downloaded model's state record. */
    suspend fun deleteModel(modelId: String)

    /** Sets the given model as the active inference model. */
    suspend fun selectModel(modelId: String)
}
