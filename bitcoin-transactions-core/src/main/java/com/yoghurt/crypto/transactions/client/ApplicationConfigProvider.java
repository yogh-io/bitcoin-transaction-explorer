package com.yoghurt.crypto.transactions.client;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.yoghurt.crypto.transactions.shared.domain.config.UserApplicationConfig;

@Singleton
public class ApplicationConfigProvider {
  private UserApplicationConfig applicationConfig;

  @Provides
  @Singleton
  public UserApplicationConfig getApplicationConfig() {
    return applicationConfig;
  }

  public void setApplicationConfig(final UserApplicationConfig applicationConfig) {
    this.applicationConfig = applicationConfig;
  }
}
