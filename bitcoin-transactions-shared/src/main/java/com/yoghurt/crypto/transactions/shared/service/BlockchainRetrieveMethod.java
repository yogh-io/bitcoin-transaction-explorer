package com.yoghurt.crypto.transactions.shared.service;

public enum BlockchainRetrieveMethod {
  GET_TRANSACTION_INFORMATION("tx"),

  GET_BLOCK_INFORMATION_FROM_HASH("block-hash"),

  GET_BLOCK_INFORMATION_FROM_HEIGHT("block-height"),

  GET_BLOCK_INFORMATION_LAST("block-last"),

  GET_LATEST_BLOCK_HASH("blockhash-last"),

  GET_ADDRESS_INFORMATION("address"),

  GET_TRANSACTION_LIST("tx-list");

  private String name;

  private BlockchainRetrieveMethod(String name) {
    this.name = name;
  }

  public static BlockchainRetrieveMethod fromName(String name) {
    for (BlockchainRetrieveMethod m : values()) {
      if (m.getName().equals(name)) {
        return m;
      }
    }

    return null;
  }

  public String getName() {
    return name;
  }
}
