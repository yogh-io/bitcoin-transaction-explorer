package com.yoghurt.crypto.transactions.server.servlets.config;

import java.util.Properties;

import com.yoghurt.crypto.transactions.server.domain.AdministratedApplicationConfig;
import com.yoghurt.crypto.transactions.server.domain.BitcoinCoreNodeConfig;
import com.yoghurt.crypto.transactions.server.domain.BlockchainSource;
import com.yoghurt.crypto.transactions.shared.domain.UserApplicationConfig;

public class ConfigFactory {
  public interface ConfigPropertiesRetriever {
    public static final String SOURCE_TYPE_KEY = "com.yoghurt.crypto.source";
    public static final String APPLICATION_TITLE_KEY = "com.yoghurt.crypto.title";
    public static final String APPLICATION_SUBTITLE_KEY = "com.yoghurt.crypto.subtitle";

    public Properties getProperties();

    public AdministratedApplicationConfig getSystemConfig();

    public UserApplicationConfig getApplicationConfig();
  }

  private static final String DEFAULT_TYPE = BlockchainSource.CORE.name();

  public static ConfigPropertiesRetriever create(final AdministratedApplicationConfig config) {
    switch (config.getBlockchainSource()) {
    case CORE:
      return new BitcoinNodeConfigRetriever((BitcoinCoreNodeConfig) config);
    default:
      throw new IllegalArgumentException();
    }
  }

  public static ConfigPropertiesRetriever create(final Properties props) {
    final String type = ((String) props.getOrDefault(ConfigPropertiesRetriever.SOURCE_TYPE_KEY, DEFAULT_TYPE)).toUpperCase();

    final BlockchainSource source = BlockchainSource.valueOf(type);

    switch (source) {
    case CORE:
      return new BitcoinNodeConfigRetriever(props);
    case BCOIN:
      return new BcoinNodeConfigRetriever(props);
    default:
      throw new IllegalArgumentException("Explorer improperly configured.");
    }
  }
}
