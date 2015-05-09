package com.yoghurt.crypto.transactions.server.servlets.config;

import java.util.Properties;

import com.yoghurt.crypto.transactions.shared.domain.config.BitcoinCoreNodeConfig;

public class BitcoinNodeConfigRetriever extends AbstractConfigRetriever<BitcoinCoreNodeConfig> {
  public static final String RPC_USER_KEY = "com.yoghurt.crypto.node.rpcUser";
  public static final String RPC_PASS_KEY = "com.yoghurt.crypto.node.rpcPass";
  public static final String HOST_KEY = "com.yoghurt.crypto.node.host";
  public static final String PORT_KEY = "com.yoghurt.crypto.node.port";

  public BitcoinNodeConfigRetriever(final BitcoinCoreNodeConfig config) {
    super(config);
  }

  public BitcoinNodeConfigRetriever(final Properties props) {
    super(props, new BitcoinCoreNodeConfig());

    config.setHost(props.getProperty(HOST_KEY));
    config.setPort(props.getProperty(PORT_KEY));
    config.setRpcUser(props.getProperty(RPC_USER_KEY));
    config.setRpcPass(props.getProperty(RPC_PASS_KEY));
  }

  @Override
  public Properties getProperties() {
    final Properties props = super.getProperties();

    props.put(HOST_KEY, config.getHost());
    props.put(PORT_KEY, config.getPort());
    props.put(RPC_USER_KEY, config.getRpcUser());
    props.put(RPC_PASS_KEY, config.getRpcPass());

    return props;
  }

  @Override
  public void attemptAutoFillProperties() {
    // Unsupported for now.
  }

}
