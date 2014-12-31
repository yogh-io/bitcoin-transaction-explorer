package com.yoghurt.crypto.transactions.server.servlets;

import com.yoghurt.crypto.transactions.server.servlets.providers.BlockchainRetrievalHook;
import com.yoghurt.crypto.transactions.server.servlets.providers.BlockrAPIRetriever;
import com.yoghurt.crypto.transactions.server.servlets.providers.JSONRPCRetriever;

public class BlockchainRetrievalFactory {
  private static final Source DEFAULT_SOURCE = Source.BLOCKR_API;

  public enum Source {
    NODE, BLOCKR_API;
  }

  private interface InnerFactory {
    BlockchainRetrievalHook create();
  }


  private final InnerFactory factory;

  private String host;
  private int port;
  private String rpcUser;
  private String rpcPass;

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
      host = System.getProperty("yoghurt.crypto.rpc.host");
      port = Integer.parseInt(System.getProperty("yoghurt.crypto.rpc.port"));
      rpcUser = System.getProperty("yoghurt.crypto.rpc.user");
      rpcPass = System.getProperty("yoghurt.crypto.rpc.pass");

      factory = new InnerFactory() {
        @Override
        public BlockchainRetrievalHook create() {
          return createBlockchainInfoHook();
        }
      };
      break;
    default:
    case BLOCKR_API:
      factory = new InnerFactory() {
        @Override
        public BlockchainRetrievalHook create() {
          return createBlockrHook();
        }
      };
      break;
    }
  }

  public BlockchainRetrievalHook create() {
    return factory.create();
  }

  public BlockchainRetrievalHook createBlockrHook() {
    return new BlockrAPIRetriever();
  }

  public BlockchainRetrievalHook createBlockchainInfoHook() {
    return new JSONRPCRetriever(host, port, rpcUser, rpcPass);
  }
}