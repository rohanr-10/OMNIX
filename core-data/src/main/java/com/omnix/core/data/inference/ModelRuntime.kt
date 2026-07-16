package com.omnix.core.data.inference

import com.omnix.core.data.repository.ModelRepository
import com.omnix.core.model.ModelInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Maintains a [StateFlow] of the currently active [ModelInfo], sourced from
 * [ModelRepository]. The [InferenceEngine] subscribes here so it always uses
 * the model the user selected in Model Manager — without duplicating state.
 *
 * Using a dedicated [CoroutineScope] tied to the process lifetime (via
 * [SupervisorJob] + [Dispatchers.Default]) means the flow stays warm even
 * when no ViewModel is attached, which prevents cold-start latency on the
 * first inference request.
 */
@Singleton
class ModelRuntime @Inject constructor(
    modelRepository: ModelRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val activeModel: StateFlow<ModelInfo?> = modelRepository
        .observeActiveModel()
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}
