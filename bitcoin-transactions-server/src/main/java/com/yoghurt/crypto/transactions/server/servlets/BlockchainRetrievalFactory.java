package com.yoghurt.crypto.transactions.server.servlets;

import java.io.IOException;
import java.util.Properties;

import com.yoghurt.crypto.transactions.server.servlets.providers.BitcoinJSONRPCRetriever;
import com.yoghurt.crypto.transactions.server.servlets.providers.BlockchainRetrievalHook;
import com.yoghurt.crypto.transactions.server.servlets.providers.BlockrAPIRetriever;

public class BlockchainRetrievalFactory {
  private static final Source DEFAULT_SOURCE = Source.BLOCKR_API;

  public enum Source {
    NODE, BLOCKR_API;
  }

  private BlockchainRetrievalHook hook;

  public BlockchainRetrievalFactory() throws IOException {
    final Properties props = new Properties();

    final String propFileName = BlockchainRetrievalProperties.class.getName().replace(".", "/") + ".properties";

    props.load(getClass().getClassLoader().getResourceAsStream(propFileName));

    Source source;
    try {
      source = Source.valueOf(props.getProperty("yoghurt.crypto.source"));
    } catch (final NullPointerException e) {
      System.out.println("Warning: No blockchain source explicitly selected, falling back to: " + DEFAULT_SOURCE.name());
      source = DEFAULT_SOURCE;
    }

    switch (source) {
    case NODE:
      final String host = props.getProperty("yoghurt.crypto.rpc.host");
      final int port = Integer.parseInt(props.getProperty("yoghurt.crypto.rpc.port"));
      final String rpcUser = props.getProperty("yoghurt.crypto.rpc.user");
      final String rpcPass = props.getProperty("yoghurt.crypto.rpc.pass");
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