// Generated by Dagger (https://dagger.dev).
package com.pg.crm.ui.customerwiseitemandratedetils;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class CustomerWiseItemAndRateDetailsViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
  @Override
  public String get() {
    return provide();
  }

  public static CustomerWiseItemAndRateDetailsViewModel_HiltModules_KeyModule_ProvideFactory create(
      ) {
    return InstanceHolder.INSTANCE;
  }

  public static String provide() {
    return Preconditions.checkNotNullFromProvides(CustomerWiseItemAndRateDetailsViewModel_HiltModules.KeyModule.provide());
  }

  private static final class InstanceHolder {
    private static final CustomerWiseItemAndRateDetailsViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new CustomerWiseItemAndRateDetailsViewModel_HiltModules_KeyModule_ProvideFactory();
  }
}
