package com.yoghurt.crypto.transactions.server.servlets.config;

import java.util.Properties;

import com.yoghurt.crypto.transactions.server.domain.AbstractAdministratedApplicationConfig;
import com.yoghurt.crypto.transactions.server.servlets.config.ConfigFactory.ConfigPropertiesRetriever;
import com.yoghurt.crypto.transactions.shared.service.domain.UserApplicationConfig;

public abstract class AbstractConfigRetriever<E extends AbstractAdministratedApplicationConfig> implements ConfigPropertiesRetriever {

  protected final E config;

  public AbstractConfigRetriever(final E config) {
    this.config = config;
  }

  public AbstractConfigRetriever(final Properties props, final E config) {
    this(config);

    config.setApplicationTitle(props.getProperty(APPLICATION_TITLE_KEY));
    config.setApplicationSubTitle(props.getProperty(APPLICATION_SUBTITLE_KEY));
  }

  @Override
  public void attemptAutoFillProperties() {
    // Defaults to no-op
  }

  @Override
  public Properties getProperties() {
    final Properties props = new Properties();

    props.put(SOURCE_TYPE_KEY, config.getBlockchainSource().name());
    props.put(APPLICATION_TITLE_KEY, config.getApplicationTitle());
    props.put(APPLICATION_SUBTITLE_KEY, config.getApplicationSubTitle());

    return props;
  }

  @Override
  public E getSystemConfig() {
    return config;
  }

  @Override
  public UserApplicationConfig getApplicationConfig() {
    final UserApplicationConfig userConfig = new UserApplicationConfig();

    userConfig.setApplicationTitle(config.getApplicationTitle());
    userConfig.setApplicationSubTitle(config.getApplicationSubTitle());

    return userConfig;
  }
}
