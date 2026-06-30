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
public final class WriterAgent_Factory implements Factory<WriterAgent> {
  @Override
  public WriterAgent get() {
    return newInstance();
  }

  public static WriterAgent_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WriterAgent newInstance() {
    return new WriterAgent();
  }

  private static final class InstanceHolder {
    private static final WriterAgent_Factory INSTANCE = new WriterAgent_Factory();
  }
}
