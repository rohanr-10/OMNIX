package com.omnix.core.data.di

import com.omnix.core.data.ai.AiResponseProvider
import com.omnix.core.data.ai.SimulatedAiResponseProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AiModule {

    @Binds
    @Singleton
    abstract fun bindAiResponseProvider(impl: SimulatedAiResponseProvider): AiResponseProvider
}
