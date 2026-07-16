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
 * Future llama.cpp JNI provider.
 *
 * To activate: implement [streamTokens] using the llama.cpp Android JNI bindings,
 * change the Hilt binding in [com.omnix.core.data.di.InferenceModule] to point here,
 * and add the native .so to the app module's jniLibs directory.
 *
 * Everything above this layer (InferenceEngine, ChatViewModel, UI) stays unchanged.
 */
@Singleton
class LlamaCppProvider @Inject constructor() : InferenceProvider {

    override val providerId: String = "llamacpp"
    override val displayName: String = "llama.cpp (Local JNI)"

    override suspend fun isAvailable(): Boolean = false

    override fun streamTokens(request: InferenceRequest): Flow<StreamingToken> = flow {
        throw InferenceException.ProviderUnavailable(providerId)
    }

    override suspend fun cancel(sessionId: String) = Unit
}
