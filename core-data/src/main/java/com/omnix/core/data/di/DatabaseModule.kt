package com.omnix.core.data.di

import android.content.Context
import androidx.room.Room
import com.omnix.core.data.local.OmnixDatabase
import com.omnix.core.data.local.dao.ChatMessageDao
import com.omnix.core.data.local.dao.ChatSessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideOmnixDatabase(@ApplicationContext context: Context): OmnixDatabase {
        return Room.databaseBuilder(
            context,
            OmnixDatabase::class.java,
            OmnixDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideChatSessionDao(database: OmnixDatabase): ChatSessionDao =
        database.chatSessionDao()

    @Provides
    fun provideChatMessageDao(database: OmnixDatabase): ChatMessageDao =
        database.chatMessageDao()
}
