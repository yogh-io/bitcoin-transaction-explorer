package com.yoghurt.crypto.transactions.shared.domain.config;

import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;

public class BlockrRetrievalHookConfig extends AbstractAdministratedApplicationConfig {
  private static final long serialVersionUID = -724907495349189557L;

  public BlockrRetrievalHookConfig() {
    super(BlockchainSource.BLOCKR_API);
  }
}
