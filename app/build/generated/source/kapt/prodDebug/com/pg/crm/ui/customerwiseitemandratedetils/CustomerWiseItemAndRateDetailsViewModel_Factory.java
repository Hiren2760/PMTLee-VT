// Generated by Dagger (https://dagger.dev).
package com.pg.crm.ui.customerwiseitemandratedetils;

import com.pg.crm.api.ApiRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class CustomerWiseItemAndRateDetailsViewModel_Factory implements Factory<CustomerWiseItemAndRateDetailsViewModel> {
  private final Provider<ApiRepository> apiRepositoryProvider;

  public CustomerWiseItemAndRateDetailsViewModel_Factory(
      Provider<ApiRepository> apiRepositoryProvider) {
    this.apiRepositoryProvider = apiRepositoryProvider;
  }

  @Override
  public CustomerWiseItemAndRateDetailsViewModel get() {
    return newInstance(apiRepositoryProvider.get());
  }

  public static CustomerWiseItemAndRateDetailsViewModel_Factory create(
      Provider<ApiRepository> apiRepositoryProvider) {
    return new CustomerWiseItemAndRateDetailsViewModel_Factory(apiRepositoryProvider);
  }

  public static CustomerWiseItemAndRateDetailsViewModel newInstance(ApiRepository apiRepository) {
    return new CustomerWiseItemAndRateDetailsViewModel(apiRepository);
  }
}