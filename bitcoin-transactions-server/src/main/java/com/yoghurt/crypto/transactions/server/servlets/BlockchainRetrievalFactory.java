package com.yoghurt.crypto.transactions.server.servlets;

import com.yoghurt.crypto.transactions.server.domain.AdministratedApplicationConfig;
import com.yoghurt.crypto.transactions.server.domain.BcoinNodeConfig;
import com.yoghurt.crypto.transactions.server.domain.BitcoinCoreNodeConfig;
import com.yoghurt.crypto.transactions.server.servlets.providers.BcoinJSONRetriever;
import com.yoghurt.crypto.transactions.server.servlets.providers.BitcoinJSONRPCRetriever;
import com.yoghurt.crypto.transactions.server.servlets.providers.BlockchainRetrievalService;

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
    set(getHook(config));
  }

  private static BlockchainRetrievalService getHook(AdministratedApplicationConfig config) {

    switch (config.getBlockchainSource()) {
    case CORE:
      return new BitcoinJSONRPCRetriever((BitcoinCoreNodeConfig) config);
    case BCOIN:
      return new BcoinJSONRetriever((BcoinNodeConfig) config);
    default:
      throw new IllegalArgumentException("Explorer improperly configured; blockchain source not set.");
    }
  }

  private static void set(final BlockchainRetrievalService hook) {
    create().hook = hook;
  }
}