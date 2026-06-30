package com.omnix.core.data.di;

import android.content.Context;
import com.omnix.core.data.local.OmnixDatabase;
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
public final class DatabaseModule_ProvideOmnixDatabaseFactory implements Factory<OmnixDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideOmnixDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public OmnixDatabase get() {
    return provideOmnixDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideOmnixDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideOmnixDatabaseFactory(contextProvider);
  }

  public static OmnixDatabase provideOmnixDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideOmnixDatabase(context));
  }
}
