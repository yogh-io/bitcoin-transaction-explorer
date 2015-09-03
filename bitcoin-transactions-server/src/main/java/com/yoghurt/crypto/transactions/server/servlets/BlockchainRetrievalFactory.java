package com.yoghurt.crypto.transactions.server.servlets;

import com.yoghurt.crypto.transactions.server.servlets.providers.BitcoinJSONRPCRetriever;
import com.yoghurt.crypto.transactions.server.servlets.providers.BlockrAPIRetriever;
import com.yoghurt.crypto.transactions.shared.domain.config.AdministratedApplicationConfig;
import com.yoghurt.crypto.transactions.shared.domain.config.BitcoinCoreNodeConfig;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalService;

public class BlockchainRetrievalFactory {
  private BlockchainRetrievalService hook;

  private static BlockchainRetrievalFactory instance;

  public static BlockchainRetrievalFactory create() {
    if(instance == null) {
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
    case NODE:
      hook = new BitcoinJSONRPCRetriever((BitcoinCoreNodeConfig) config);
      break;
    default:
    case BLOCKR_API:
      hook = new BlockrAPIRetriever();
      break;
    }

    set(hook);
  }

  private static void set(final BlockchainRetrievalService hook) {
    create().hook = hook;
  }
}