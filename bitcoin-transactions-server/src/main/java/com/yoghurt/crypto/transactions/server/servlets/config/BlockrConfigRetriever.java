package com.yoghurt.crypto.transactions.server.servlets.config;

import java.util.Properties;

import com.yoghurt.crypto.transactions.shared.domain.config.BlockrRetrievalHookConfig;

public class BlockrConfigRetriever extends AbstractConfigRetriever<BlockrRetrievalHookConfig> {
  public BlockrConfigRetriever() {
    this(new BlockrRetrievalHookConfig());
  }

  public BlockrConfigRetriever(final BlockrRetrievalHookConfig config) {
    super(config);
  }

  public BlockrConfigRetriever(final Properties props) {
    this();
  }
}
