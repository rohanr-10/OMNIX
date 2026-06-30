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
public final class ModeratorAgent_Factory implements Factory<ModeratorAgent> {
  @Override
  public ModeratorAgent get() {
    return newInstance();
  }

  public static ModeratorAgent_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ModeratorAgent newInstance() {
    return new ModeratorAgent();
  }

  private static final class InstanceHolder {
    private static final ModeratorAgent_Factory INSTANCE = new ModeratorAgent_Factory();
  }
}
