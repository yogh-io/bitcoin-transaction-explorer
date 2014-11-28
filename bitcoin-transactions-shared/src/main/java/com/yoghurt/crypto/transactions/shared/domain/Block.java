package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

public class Block implements Serializable {
  private static final long serialVersionUID = -2309928857207399375L;

  private byte[] hash;

  public Block() {}

  public byte[] getHash() {
    return hash;
  }

  public void setHash(byte[] hash) {
    this.hash = hash;
  }
}
