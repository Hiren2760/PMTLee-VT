// Generated by Dagger (https://dagger.dev).
package com.pg.crm.ui.hardening;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class HardeningProcessViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
  @Override
  public String get() {
    return provide();
  }

  public static HardeningProcessViewModel_HiltModules_KeyModule_ProvideFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provide() {
    return Preconditions.checkNotNullFromProvides(HardeningProcessViewModel_HiltModules.KeyModule.provide());
  }

  private static final class InstanceHolder {
    private static final HardeningProcessViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new HardeningProcessViewModel_HiltModules_KeyModule_ProvideFactory();
  }
}
