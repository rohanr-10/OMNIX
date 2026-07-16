package com.omnix.core.data.di

import com.omnix.core.data.ai.AiResponseProvider
import com.omnix.core.data.inference.InferenceEngine
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Binds [AiResponseProvider] to [InferenceEngine].
 *
 * [InferenceEngine] implements [AiResponseProvider] directly, so
 * [com.omnix.feature.chat.ChatViewModel] is completely unchanged — it still
 * injects [AiResponseProvider] and the full inference pipeline activates
 * transparently behind it.
 *
 * Previous binding was to [com.omnix.core.data.ai.SimulatedAiResponseProvider].
 * That class is retained as a reference but is no longer bound.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AiModule {

    @Binds
    @Singleton
    abstract fun bindAiResponseProvider(impl: InferenceEngine): AiResponseProvider
}
