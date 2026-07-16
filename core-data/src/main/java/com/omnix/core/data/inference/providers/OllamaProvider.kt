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
 * Future Ollama HTTP streaming provider.
 *
 * To activate: implement [streamTokens] with an OkHttp/Ktor client pointing
 * at the Ollama `/api/generate` SSE endpoint, change the Hilt binding in
 * [com.omnix.core.data.di.InferenceModule], and add the INTERNET permission
 * to the manifest (it is already declared in app/AndroidManifest.xml).
 *
 * Everything above this layer (InferenceEngine, ChatViewModel, UI) stays unchanged.
 */
@Singleton
class OllamaProvider @Inject constructor() : InferenceProvider {

    override val providerId: String = "ollama"
    override val displayName: String = "Ollama (HTTP)"

    override suspend fun isAvailable(): Boolean = false

    override fun streamTokens(request: InferenceRequest): Flow<StreamingToken> = flow {
        throw InferenceException.ProviderUnavailable(providerId)
    }

    override suspend fun cancel(sessionId: String) = Unit
}
