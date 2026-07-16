package com.omnix.core.data.di

import com.omnix.core.data.inference.InferenceProvider
import com.omnix.core.data.inference.providers.LlamaCppProvider
import com.omnix.core.data.inference.providers.MediaPipeProvider
import com.omnix.core.data.inference.providers.OllamaProvider
import com.omnix.core.data.inference.providers.SimulatedInferenceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Hilt DI for the inference subsystem.
 *
 * The multibound set of [InferenceProvider]s is kept for future use (e.g. a
 * settings screen that lets the user pick their preferred backend). Currently
 * only [SimulatedInferenceProvider] is the active binding for [InferenceProvider].
 *
 * To switch backends: change [bindActiveProvider] to point to any other
 * registered provider. Nothing outside this module needs to change.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class InferenceModule {

    /**
     * The active [InferenceProvider] injected into [com.omnix.core.data.inference.InferenceEngine].
     * Change this binding to swap the backend.
     */
    @Binds
    @Singleton
    abstract fun bindActiveProvider(impl: SimulatedInferenceProvider): InferenceProvider

    // ─── Registry (all providers known to the system) ─────────────────────────

    @Binds
    @IntoSet
    abstract fun bindSimulatedProvider(impl: SimulatedInferenceProvider): InferenceProvider

    @Binds
    @IntoSet
    abstract fun bindLlamaCppProvider(impl: LlamaCppProvider): InferenceProvider

    @Binds
    @IntoSet
    abstract fun bindOllamaProvider(impl: OllamaProvider): InferenceProvider

    @Binds
    @IntoSet
    abstract fun bindMediaPipeProvider(impl: MediaPipeProvider): InferenceProvider
}
