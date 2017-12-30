package com.yoghurt.crypto.transactions.server.servlets.config;

import java.util.Properties;

import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;
import com.yoghurt.crypto.transactions.shared.domain.config.AdministratedApplicationConfig;
import com.yoghurt.crypto.transactions.shared.domain.config.BitcoinCoreNodeConfig;
import com.yoghurt.crypto.transactions.shared.domain.config.UserApplicationConfig;

public class ConfigFactory {
  public interface ConfigPropertiesRetriever {
    public static final String SOURCE_TYPE_KEY = "com.yoghurt.crypto.source";
    public static final String APPLICATION_TITLE_KEY = "com.yoghurt.crypto.title";
    public static final String APPLICATION_SUBTITLE_KEY = "com.yoghurt.crypto.subtitle";
    public static final String HOST_DONATION_ADDRESS_KEY = "com.yoghurt.crypto.hostDonationAddress";
    public static final String PROJECT_DONATION_ADDRESS_KEY = "com.yoghurt.crypto.projectDonationAddress";

    public Properties getProperties();

    public void attemptAutoFillProperties();

    public AdministratedApplicationConfig getSystemConfig();

    public UserApplicationConfig getApplicationConfig();
  }

  public static ConfigPropertiesRetriever create(final AdministratedApplicationConfig config) {
    switch (config.getBlockchainSource()) {
    case NODE:
      return new BitcoinNodeConfigRetriever((BitcoinCoreNodeConfig) config);
    default:
      throw new IllegalArgumentException();
    }
  }

  public static ConfigPropertiesRetriever create(final Properties props) {
    final String type = (String) props.get(ConfigPropertiesRetriever.SOURCE_TYPE_KEY);

    final BlockchainSource source = type == null ? BlockchainSource.NODE : BlockchainSource.valueOf(type);

    switch (source) {
    default:
    case NODE:
      return new BitcoinNodeConfigRetriever(props);
    }
  }
}
