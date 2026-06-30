package com.omnix.core.data.council;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.util.Set;
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
public final class CouncilAgentRegistry_Factory implements Factory<CouncilAgentRegistry> {
  private final Provider<Set<CouncilAgent>> agentsProvider;

  public CouncilAgentRegistry_Factory(Provider<Set<CouncilAgent>> agentsProvider) {
    this.agentsProvider = agentsProvider;
  }

  @Override
  public CouncilAgentRegistry get() {
    return newInstance(agentsProvider.get());
  }

  public static CouncilAgentRegistry_Factory create(Provider<Set<CouncilAgent>> agentsProvider) {
    return new CouncilAgentRegistry_Factory(agentsProvider);
  }

  public static CouncilAgentRegistry newInstance(Set<CouncilAgent> agents) {
    return new CouncilAgentRegistry(agents);
  }
}
