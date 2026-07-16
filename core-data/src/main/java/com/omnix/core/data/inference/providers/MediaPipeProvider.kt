package com.omnix.core.data.inference.providers

import com.omnix.core.data.inference.InferenceException
import com.omnix.core.data.inference.InferenceProvider
import com.omnix.core.data.inference.InferenceRequest
import com.omnix.core.data.inference.StreamingToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Future MediaPipe LLM Inference API provider.
 *
 * To activate: add the `com.google.mediapipe:tasks-genai` dependency to
 * `:core-data`'s build.gradle.kts, implement [streamTokens] using the
 * `LlmInference` API, and change the Hilt binding in
 * [com.omnix.core.data.di.InferenceModule].
 *
 * Particularly well-suited to Gemma models on supported Pixel/Tensor devices
 * where GPU/NPU acceleration is available.
 *
 * Everything above this layer (InferenceEngine, ChatViewModel, UI) stays unchanged.
 */
@Singleton
class MediaPipeProvider @Inject constructor() : InferenceProvider {

    override val providerId: String = "mediapipe"
    override val displayName: String = "MediaPipe LLM (GPU/NPU)"

    override suspend fun isAvailable(): Boolean = false

    override fun streamTokens(request: InferenceRequest): Flow<StreamingToken> = flow {
        throw InferenceException.ProviderUnavailable(providerId)
    }

    override suspend fun cancel(sessionId: String) = Unit
}
