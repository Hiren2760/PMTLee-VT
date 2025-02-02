// Generated by Dagger (https://dagger.dev).
package com.pg.crm.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ApiRepository_Factory implements Factory<ApiRepository> {
  private final Provider<ApiService> apiServiceProvider;

  public ApiRepository_Factory(Provider<ApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public ApiRepository get() {
    return newInstance(apiServiceProvider.get());
  }

  public static ApiRepository_Factory create(Provider<ApiService> apiServiceProvider) {
    return new ApiRepository_Factory(apiServiceProvider);
  }

  public static ApiRepository newInstance(ApiService apiService) {
    return new ApiRepository(apiService);
  }
}
