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
public final class EngineerAgent_Factory implements Factory<EngineerAgent> {
  @Override
  public EngineerAgent get() {
    return newInstance();
  }

  public static EngineerAgent_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static EngineerAgent newInstance() {
    return new EngineerAgent();
  }

  private static final class InstanceHolder {
    private static final EngineerAgent_Factory INSTANCE = new EngineerAgent_Factory();
  }
}
