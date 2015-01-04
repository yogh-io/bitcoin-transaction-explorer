package com.yoghurt.crypto.transactions.server.servlets;

import com.yoghurt.crypto.transactions.server.servlets.providers.BitcoinJSONRPCRetriever;
import com.yoghurt.crypto.transactions.server.servlets.providers.BlockchainRetrievalHook;
import com.yoghurt.crypto.transactions.server.servlets.providers.BlockrAPIRetriever;

public class BlockchainRetrievalFactory {
  private static final Source DEFAULT_SOURCE = Source.BLOCKR_API;

  public enum Source {
    NODE, BLOCKR_API;
  }

  private BlockchainRetrievalHook hook;

  public BlockchainRetrievalFactory() {
    Source source;
    try {
      source = Source.valueOf(System.getProperty("yoghurt.crypto.source"));
    } catch (final NullPointerException e) {
      System.out.println("Warning: No blockchain source explicitly selected, falling back to: " + DEFAULT_SOURCE.name());
      source = DEFAULT_SOURCE;
    }

    switch (source) {
    case NODE:
      final String host = System.getProperty("yoghurt.crypto.rpc.host");
      final int port = Integer.parseInt(System.getProperty("yoghurt.crypto.rpc.port"));
      final String rpcUser = System.getProperty("yoghurt.crypto.rpc.user");
      final String rpcPass = System.getProperty("yoghurt.crypto.rpc.pass");
      hook = new BitcoinJSONRPCRetriever(host, port, rpcUser, rpcPass);
      break;
    default:
    case BLOCKR_API:
      hook = new BlockrAPIRetriever();
      break;
    }
  }

  public BlockchainRetrievalHook get() {
    return hook;
  }
}