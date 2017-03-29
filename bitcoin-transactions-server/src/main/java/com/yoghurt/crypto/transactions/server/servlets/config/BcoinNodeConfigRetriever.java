package com.yoghurt.crypto.transactions.server.servlets.config;

import java.util.Properties;

import com.yoghurt.crypto.transactions.server.domain.BcoinNodeConfig;

public class BcoinNodeConfigRetriever extends AbstractConfigRetriever<BcoinNodeConfig> {
  public static final String RPC_USER_KEY = "com.yoghurt.crypto.bcoin.apiKey";
  public static final String HOST_KEY = "com.yoghurt.crypto.bcoin.host";
  public static final String PORT_KEY = "com.yoghurt.crypto.bcoin.port";

  public BcoinNodeConfigRetriever(final BcoinNodeConfig config) {
    super(config);
  }

  public BcoinNodeConfigRetriever(final Properties props) {
    super(props, new BcoinNodeConfig());

    config.setHost(props.getProperty(HOST_KEY));
    config.setPort(props.getProperty(PORT_KEY));
    config.setRpcKey(props.getProperty(RPC_USER_KEY));
  }

  @Override
  public Properties getProperties() {
    final Properties props = super.getProperties();

    props.put(HOST_KEY, config.getHost());
    props.put(PORT_KEY, config.getPort());
    props.put(RPC_USER_KEY, config.getRpcKey());

    return props;
  }
}
