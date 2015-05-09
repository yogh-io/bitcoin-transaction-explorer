package com.yoghurt.crypto.transactions.server.servlets.config;

import java.util.Properties;

import com.yoghurt.crypto.transactions.shared.domain.config.BlockrRetrievalHookConfig;

public class BlockrConfigRetriever extends AbstractConfigRetriever<BlockrRetrievalHookConfig> {
  public BlockrConfigRetriever(final Properties props, final BlockrRetrievalHookConfig config) {
    super(props, config);
  }

  public BlockrConfigRetriever(Properties props) {
    this(props, new BlockrRetrievalHookConfig());
  }

  public BlockrConfigRetriever(BlockrRetrievalHookConfig config) {
    super(config);
  }
}
