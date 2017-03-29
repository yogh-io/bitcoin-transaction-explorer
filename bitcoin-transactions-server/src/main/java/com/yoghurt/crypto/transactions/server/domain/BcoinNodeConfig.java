package com.yoghurt.crypto.transactions.server.domain;

public class BcoinNodeConfig extends AbstractAdministratedApplicationConfig {
  private static final long serialVersionUID = 1813626460749181702L;

  private String host;
  private String port;
  private String rpcKey;

  public BcoinNodeConfig() {
    super(BlockchainSource.BCOIN);
  }

  public String getHost() {
    return host;
  }

  public void setHost(final String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(final String port) {
    this.port = port;
  }

  public String getRpcKey() {
    return rpcKey;
  }

  public void setRpcKey(final String rpcKey) {
    this.rpcKey = rpcKey;
  }
}
