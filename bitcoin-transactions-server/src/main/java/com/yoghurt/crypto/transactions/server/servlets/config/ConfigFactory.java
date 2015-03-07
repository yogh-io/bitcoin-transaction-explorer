package com.yoghurt.crypto.transactions.server.servlets.config;

import java.util.Properties;

import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;
import com.yoghurt.crypto.transactions.shared.domain.config.BitcoinCoreNodeConfig;
import com.yoghurt.crypto.transactions.shared.domain.config.BlockrRetrievalHookConfig;
import com.yoghurt.crypto.transactions.shared.domain.config.RetrievalHookConfig;

public class ConfigFactory {
  public interface ConfigPropertiesRetriever {
    public static final String SOURCE_TYPE_KEY = "com.yoghurt.crypto.source";

    public Properties getProperties();

    public void attemptAutoFillProperties();

    public RetrievalHookConfig getConfig();
  }

  public static ConfigPropertiesRetriever create(final RetrievalHookConfig config) {
    switch(config.getBlockchainSource()) {
    case BLOCKR_API:
      return new BlockrConfigRetriever((BlockrRetrievalHookConfig)config);
    case NODE:
      return new BitcoinNodeConfigRetriever((BitcoinCoreNodeConfig) config);
    default:
      throw new IllegalArgumentException();
    }
  }

  public static ConfigPropertiesRetriever create(final Properties props) {
    final String type = (String) props.get(ConfigPropertiesRetriever.SOURCE_TYPE_KEY);

    final BlockchainSource source = type == null ? BlockchainSource.BLOCKR_API : BlockchainSource.valueOf(type);

    switch(source) {
    case NODE:
      return new BitcoinNodeConfigRetriever(props);
    case BLOCKR_API:
    default:
      return new BlockrConfigRetriever(props);
    }
  }
}
