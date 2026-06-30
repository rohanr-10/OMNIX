package com.omnix.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

/**
 * Centralizes coroutine dispatcher provisioning so call sites never reference
 * Dispatchers.IO / Dispatchers.Default directly, keeping repositories and
 * use cases unit-testable via dispatcher substitution.
 */
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @IoDispatcher
    @Provides
    @Singleton
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Provides
    @Singleton
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @MainDispatcher
    @Provides
    @Singleton
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
