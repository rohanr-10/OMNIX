package com.omnix.core.data.di;

import com.omnix.core.data.local.OmnixDatabase;
import com.omnix.core.data.local.dao.ChatSessionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class DatabaseModule_ProvideChatSessionDaoFactory implements Factory<ChatSessionDao> {
  private final Provider<OmnixDatabase> databaseProvider;

  public DatabaseModule_ProvideChatSessionDaoFactory(Provider<OmnixDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ChatSessionDao get() {
    return provideChatSessionDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideChatSessionDaoFactory create(
      Provider<OmnixDatabase> databaseProvider) {
    return new DatabaseModule_ProvideChatSessionDaoFactory(databaseProvider);
  }

  public static ChatSessionDao provideChatSessionDao(OmnixDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideChatSessionDao(database));
  }
}
