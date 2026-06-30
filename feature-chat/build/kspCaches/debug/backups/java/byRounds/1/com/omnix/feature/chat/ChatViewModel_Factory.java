package com.omnix.feature.chat;

import androidx.lifecycle.SavedStateHandle;
import com.omnix.core.data.ai.AiResponseProvider;
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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<ChatRepository> chatRepositoryProvider;

  private final Provider<AiResponseProvider> aiResponseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public ChatViewModel_Factory(Provider<ChatRepository> chatRepositoryProvider,
      Provider<AiResponseProvider> aiResponseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.chatRepositoryProvider = chatRepositoryProvider;
    this.aiResponseProvider = aiResponseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(chatRepositoryProvider.get(), aiResponseProvider.get(), savedStateHandleProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<ChatRepository> chatRepositoryProvider,
      Provider<AiResponseProvider> aiResponseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new ChatViewModel_Factory(chatRepositoryProvider, aiResponseProvider, savedStateHandleProvider);
  }

  public static ChatViewModel newInstance(ChatRepository chatRepository,
      AiResponseProvider aiResponseProvider, SavedStateHandle savedStateHandle) {
    return new ChatViewModel(chatRepository, aiResponseProvider, savedStateHandle);
  }
}
