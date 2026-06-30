package com.omnix.core.data.council.agents;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class SafetyAgent_Factory implements Factory<SafetyAgent> {
  @Override
  public SafetyAgent get() {
    return newInstance();
  }

  public static SafetyAgent_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SafetyAgent newInstance() {
    return new SafetyAgent();
  }

  private static final class InstanceHolder {
    private static final SafetyAgent_Factory INSTANCE = new SafetyAgent_Factory();
  }
}
