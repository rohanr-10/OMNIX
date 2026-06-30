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
public final class SkepticAgent_Factory implements Factory<SkepticAgent> {
  @Override
  public SkepticAgent get() {
    return newInstance();
  }

  public static SkepticAgent_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SkepticAgent newInstance() {
    return new SkepticAgent();
  }

  private static final class InstanceHolder {
    private static final SkepticAgent_Factory INSTANCE = new SkepticAgent_Factory();
  }
}
