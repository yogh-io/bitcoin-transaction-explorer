package com.yoghurt.crypto.transactions.shared.domain.config;

import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;

public class BitcoinCoreNodeConfig extends AbstractAdministratedApplicationConfig {
  private static final long serialVersionUID = -9111194393899530619L;

  private String host;
  private String port;
  private String rpcUser;
  private String rpcPass;

  public BitcoinCoreNodeConfig() {
    super(BlockchainSource.NODE);
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

  public String getRpcUser() {
    return rpcUser;
  }

  public void setRpcUser(final String rpcUser) {
    this.rpcUser = rpcUser;
  }

  public String getRpcPass() {
    return rpcPass;
  }

  public void setRpcPass(final String rpcPass) {
    this.rpcPass = rpcPass;
  }

}
