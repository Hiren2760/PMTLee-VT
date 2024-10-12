// Generated by Dagger (https://dagger.dev).
package com.pg.crm.ui.hardeningVacuum;

import com.pg.crm.api.ApiRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class HardeningVacuumViewModel_Factory implements Factory<HardeningVacuumViewModel> {
  private final Provider<ApiRepository> apiRepositoryProvider;

  public HardeningVacuumViewModel_Factory(Provider<ApiRepository> apiRepositoryProvider) {
    this.apiRepositoryProvider = apiRepositoryProvider;
  }

  @Override
  public HardeningVacuumViewModel get() {
    return newInstance(apiRepositoryProvider.get());
  }

  public static HardeningVacuumViewModel_Factory create(
      Provider<ApiRepository> apiRepositoryProvider) {
    return new HardeningVacuumViewModel_Factory(apiRepositoryProvider);
  }

  public static HardeningVacuumViewModel newInstance(ApiRepository apiRepository) {
    return new HardeningVacuumViewModel(apiRepository);
  }
}