package com.yoghurt.crypto.transactions.server.servlets;

import com.yoghurt.crypto.transactions.server.domain.AdministratedApplicationConfig;
import com.yoghurt.crypto.transactions.server.domain.BitcoinCoreNodeConfig;
import com.yoghurt.crypto.transactions.server.servlets.providers.BitcoinJSONRPCRetriever;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalService;

public class BlockchainRetrievalFactory {
  private BlockchainRetrievalService hook;

  private static BlockchainRetrievalFactory instance;

  public static BlockchainRetrievalFactory create() {
    if (instance == null) {
      instance = new BlockchainRetrievalFactory();
    }

    return instance;
  }

  public static BlockchainRetrievalService get() {
    return create().hook;
  }

  public static void set(final AdministratedApplicationConfig config) {
    BlockchainRetrievalService hook;

    switch (config.getBlockchainSource()) {
    case CORE:
      hook = new BitcoinJSONRPCRetriever((BitcoinCoreNodeConfig) config);
      break;
    default:
      throw new IllegalArgumentException("Explorer improperly configured; blockchain source not set.");
    }

    set(hook);
  }

  private static void set(final BlockchainRetrievalService hook) {
    create().hook = hook;
  }
}