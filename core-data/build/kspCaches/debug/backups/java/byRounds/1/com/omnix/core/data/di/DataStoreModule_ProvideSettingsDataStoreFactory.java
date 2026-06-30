package com.omnix.core.data.di;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class DataStoreModule_ProvideSettingsDataStoreFactory implements Factory<DataStore<Preferences>> {
  private final Provider<Context> contextProvider;

  public DataStoreModule_ProvideSettingsDataStoreFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DataStore<Preferences> get() {
    return provideSettingsDataStore(contextProvider.get());
  }

  public static DataStoreModule_ProvideSettingsDataStoreFactory create(
      Provider<Context> contextProvider) {
    return new DataStoreModule_ProvideSettingsDataStoreFactory(contextProvider);
  }

  public static DataStore<Preferences> provideSettingsDataStore(Context context) {
    return Preconditions.checkNotNullFromProvides(DataStoreModule.INSTANCE.provideSettingsDataStore(context));
  }
}
