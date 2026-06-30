package com.omnix.core.data.di;

import com.omnix.core.data.local.OmnixDatabase;
import com.omnix.core.data.local.dao.ChatMessageDao;
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
public final class DatabaseModule_ProvideChatMessageDaoFactory implements Factory<ChatMessageDao> {
  private final Provider<OmnixDatabase> databaseProvider;

  public DatabaseModule_ProvideChatMessageDaoFactory(Provider<OmnixDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ChatMessageDao get() {
    return provideChatMessageDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideChatMessageDaoFactory create(
      Provider<OmnixDatabase> databaseProvider) {
    return new DatabaseModule_ProvideChatMessageDaoFactory(databaseProvider);
  }

  public static ChatMessageDao provideChatMessageDao(OmnixDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideChatMessageDao(database));
  }
}
