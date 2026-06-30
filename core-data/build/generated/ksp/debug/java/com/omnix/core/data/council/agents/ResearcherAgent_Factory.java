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
public final class ResearcherAgent_Factory implements Factory<ResearcherAgent> {
  @Override
  public ResearcherAgent get() {
    return newInstance();
  }

  public static ResearcherAgent_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ResearcherAgent newInstance() {
    return new ResearcherAgent();
  }

  private static final class InstanceHolder {
    private static final ResearcherAgent_Factory INSTANCE = new ResearcherAgent_Factory();
  }
}
