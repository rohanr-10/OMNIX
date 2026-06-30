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
public final class PlannerAgent_Factory implements Factory<PlannerAgent> {
  @Override
  public PlannerAgent get() {
    return newInstance();
  }

  public static PlannerAgent_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PlannerAgent newInstance() {
    return new PlannerAgent();
  }

  private static final class InstanceHolder {
    private static final PlannerAgent_Factory INSTANCE = new PlannerAgent_Factory();
  }
}
