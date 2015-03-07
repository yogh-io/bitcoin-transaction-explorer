package com.yoghurt.crypto.transactions.shared.domain.config;

import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;

public abstract class AbstractRetrievalHookConfig implements RetrievalHookConfig {
  private static final long serialVersionUID = 1194639229241504631L;

  private BlockchainSource source;

  public AbstractRetrievalHookConfig(final BlockchainSource source) {
    this.source = source;
  }

  @Override
  public BlockchainSource getBlockchainSource() {
    return source;
  }
}
