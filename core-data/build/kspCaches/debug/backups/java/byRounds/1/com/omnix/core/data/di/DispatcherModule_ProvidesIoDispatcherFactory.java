package com.omnix.core.data.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.omnix.core.data.di.IoDispatcher")
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
public final class DispatcherModule_ProvidesIoDispatcherFactory implements Factory<CoroutineDispatcher> {
  @Override
  public CoroutineDispatcher get() {
    return providesIoDispatcher();
  }

  public static DispatcherModule_ProvidesIoDispatcherFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CoroutineDispatcher providesIoDispatcher() {
    return Preconditions.checkNotNullFromProvides(DispatcherModule.INSTANCE.providesIoDispatcher());
  }

  private static final class InstanceHolder {
    private static final DispatcherModule_ProvidesIoDispatcherFactory INSTANCE = new DispatcherModule_ProvidesIoDispatcherFactory();
  }
}
