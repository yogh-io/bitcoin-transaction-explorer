package com.yoghurt.crypto.transactions.server.servlets.config;

import java.util.Properties;

import com.yoghurt.crypto.transactions.server.servlets.config.ConfigFactory.ConfigPropertiesRetriever;
import com.yoghurt.crypto.transactions.shared.domain.config.RetrievalHookConfig;

public abstract class AbstractConfigRetriever<E extends RetrievalHookConfig> implements ConfigPropertiesRetriever {

  protected final E config;

  public AbstractConfigRetriever(final E config) {
    this.config = config;
  }

  @Override
  public void attemptAutoFillProperties() {
    // Defaults to no-op
  }

  @Override
  public Properties getProperties() {
    final Properties props = new Properties();

    props.put(SOURCE_TYPE_KEY, config.getBlockchainSource().name());

    return props;
  }

  @Override
  public E getConfig() {
    return config;
  }
}
