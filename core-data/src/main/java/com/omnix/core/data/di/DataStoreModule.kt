package com.omnix.core.data.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val SETTINGS_DATASTORE_NAME = "omnix_settings"

private val Context.settingsDataStore by preferencesDataStore(name = SETTINGS_DATASTORE_NAME)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context) =
        context.settingsDataStore
}
