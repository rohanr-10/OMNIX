package com.omnix.core.data.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.omnix.core.data.di.DefaultDispatcher")
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
public final class DispatcherModule_ProvidesDefaultDispatcherFactory implements Factory<CoroutineDispatcher> {
  @Override
  public CoroutineDispatcher get() {
    return providesDefaultDispatcher();
  }

  public static DispatcherModule_ProvidesDefaultDispatcherFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CoroutineDispatcher providesDefaultDispatcher() {
    return Preconditions.checkNotNullFromProvides(DispatcherModule.INSTANCE.providesDefaultDispatcher());
  }

  private static final class InstanceHolder {
    private static final DispatcherModule_ProvidesDefaultDispatcherFactory INSTANCE = new DispatcherModule_ProvidesDefaultDispatcherFactory();
  }
}
