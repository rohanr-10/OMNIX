package com.omnix.feature.council;

import androidx.lifecycle.SavedStateHandle;
import com.omnix.core.data.council.CouncilOrchestrator;
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
public final class CouncilViewModel_Factory implements Factory<CouncilViewModel> {
  private final Provider<CouncilOrchestrator> orchestratorProvider;

  private final Provider<ChatRepository> chatRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public CouncilViewModel_Factory(Provider<CouncilOrchestrator> orchestratorProvider,
      Provider<ChatRepository> chatRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.orchestratorProvider = orchestratorProvider;
    this.chatRepositoryProvider = chatRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public CouncilViewModel get() {
    return newInstance(orchestratorProvider.get(), chatRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static CouncilViewModel_Factory create(Provider<CouncilOrchestrator> orchestratorProvider,
      Provider<ChatRepository> chatRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new CouncilViewModel_Factory(orchestratorProvider, chatRepositoryProvider, savedStateHandleProvider);
  }

  public static CouncilViewModel newInstance(CouncilOrchestrator orchestrator,
      ChatRepository chatRepository, SavedStateHandle savedStateHandle) {
    return new CouncilViewModel(orchestrator, chatRepository, savedStateHandle);
  }
}
