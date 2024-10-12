// Generated by Dagger (https://dagger.dev).
package com.pg.crm.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import okhttp3.OkHttpClient;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class NetworkModule_ProvideHttpClientFactory implements Factory<OkHttpClient> {
  @Override
  public OkHttpClient get() {
    return provideHttpClient();
  }

  public static NetworkModule_ProvideHttpClientFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static OkHttpClient provideHttpClient() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideHttpClient());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideHttpClientFactory INSTANCE = new NetworkModule_ProvideHttpClientFactory();
  }
}
