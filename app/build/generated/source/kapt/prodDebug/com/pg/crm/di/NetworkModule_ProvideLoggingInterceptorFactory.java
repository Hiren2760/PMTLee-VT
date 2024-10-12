// Generated by Dagger (https://dagger.dev).
package com.pg.crm.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import okhttp3.logging.HttpLoggingInterceptor;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class NetworkModule_ProvideLoggingInterceptorFactory implements Factory<HttpLoggingInterceptor> {
  @Override
  public HttpLoggingInterceptor get() {
    return provideLoggingInterceptor();
  }

  public static NetworkModule_ProvideLoggingInterceptorFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static HttpLoggingInterceptor provideLoggingInterceptor() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideLoggingInterceptor());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideLoggingInterceptorFactory INSTANCE = new NetworkModule_ProvideLoggingInterceptorFactory();
  }
}
