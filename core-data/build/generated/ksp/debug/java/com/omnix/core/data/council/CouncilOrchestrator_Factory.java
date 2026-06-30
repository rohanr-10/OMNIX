package com.omnix.core.data.council;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class CouncilOrchestrator_Factory implements Factory<CouncilOrchestrator> {
  private final Provider<CouncilAgentRegistry> agentRegistryProvider;

  public CouncilOrchestrator_Factory(Provider<CouncilAgentRegistry> agentRegistryProvider) {
    this.agentRegistryProvider = agentRegistryProvider;
  }

  @Override
  public CouncilOrchestrator get() {
    return newInstance(agentRegistryProvider.get());
  }

  public static CouncilOrchestrator_Factory create(
      Provider<CouncilAgentRegistry> agentRegistryProvider) {
    return new CouncilOrchestrator_Factory(agentRegistryProvider);
  }

  public static CouncilOrchestrator newInstance(CouncilAgentRegistry agentRegistry) {
    return new CouncilOrchestrator(agentRegistry);
  }
}
