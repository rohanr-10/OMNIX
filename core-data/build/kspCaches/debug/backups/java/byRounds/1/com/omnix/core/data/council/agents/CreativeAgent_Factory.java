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
public final class CreativeAgent_Factory implements Factory<CreativeAgent> {
  @Override
  public CreativeAgent get() {
    return newInstance();
  }

  public static CreativeAgent_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CreativeAgent newInstance() {
    return new CreativeAgent();
  }

  private static final class InstanceHolder {
    private static final CreativeAgent_Factory INSTANCE = new CreativeAgent_Factory();
  }
}
