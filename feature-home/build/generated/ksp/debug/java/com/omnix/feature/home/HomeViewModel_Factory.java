package com.omnix.feature.home;

import com.omnix.core.data.repository.ChatRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<ChatRepository> chatRepositoryProvider;

  public HomeViewModel_Factory(Provider<ChatRepository> chatRepositoryProvider) {
    this.chatRepositoryProvider = chatRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(chatRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<ChatRepository> chatRepositoryProvider) {
    return new HomeViewModel_Factory(chatRepositoryProvider);
  }

  public static HomeViewModel newInstance(ChatRepository chatRepository) {
    return new HomeViewModel(chatRepository);
  }
}
