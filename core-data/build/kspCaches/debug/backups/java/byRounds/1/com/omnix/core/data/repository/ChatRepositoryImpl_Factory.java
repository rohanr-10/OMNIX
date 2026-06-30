package com.omnix.core.data.repository;

import com.omnix.core.data.local.dao.ChatMessageDao;
import com.omnix.core.data.local.dao.ChatSessionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.omnix.core.data.di.IoDispatcher")
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
public final class ChatRepositoryImpl_Factory implements Factory<ChatRepositoryImpl> {
  private final Provider<ChatSessionDao> sessionDaoProvider;

  private final Provider<ChatMessageDao> messageDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public ChatRepositoryImpl_Factory(Provider<ChatSessionDao> sessionDaoProvider,
      Provider<ChatMessageDao> messageDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.sessionDaoProvider = sessionDaoProvider;
    this.messageDaoProvider = messageDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public ChatRepositoryImpl get() {
    return newInstance(sessionDaoProvider.get(), messageDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static ChatRepositoryImpl_Factory create(Provider<ChatSessionDao> sessionDaoProvider,
      Provider<ChatMessageDao> messageDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new ChatRepositoryImpl_Factory(sessionDaoProvider, messageDaoProvider, ioDispatcherProvider);
  }

  public static ChatRepositoryImpl newInstance(ChatSessionDao sessionDao, ChatMessageDao messageDao,
      CoroutineDispatcher ioDispatcher) {
    return new ChatRepositoryImpl(sessionDao, messageDao, ioDispatcher);
  }
}
