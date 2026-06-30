package com.omnix.core.data.ai;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class SimulatedAiResponseProvider_Factory implements Factory<SimulatedAiResponseProvider> {
  @Override
  public SimulatedAiResponseProvider get() {
    return newInstance();
  }

  public static SimulatedAiResponseProvider_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SimulatedAiResponseProvider newInstance() {
    return new SimulatedAiResponseProvider();
  }

  private static final class InstanceHolder {
    private static final SimulatedAiResponseProvider_Factory INSTANCE = new SimulatedAiResponseProvider_Factory();
  }
}
