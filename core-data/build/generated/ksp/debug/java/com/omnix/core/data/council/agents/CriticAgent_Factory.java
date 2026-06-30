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
public final class CriticAgent_Factory implements Factory<CriticAgent> {
  @Override
  public CriticAgent get() {
    return newInstance();
  }

  public static CriticAgent_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CriticAgent newInstance() {
    return new CriticAgent();
  }

  private static final class InstanceHolder {
    private static final CriticAgent_Factory INSTANCE = new CriticAgent_Factory();
  }
}
