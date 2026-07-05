package com.omnix.core.data.di

import com.omnix.core.data.repository.ChatRepository
import com.omnix.core.data.repository.ChatRepositoryImpl
import com.omnix.core.data.repository.ModelRepository
import com.omnix.core.data.repository.ModelRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChatRepository(impl: ChatRepositoryImpl): ChatRepository

    @Binds
    @Singleton
    abstract fun bindModelRepository(impl: ModelRepositoryImpl): ModelRepository
}
