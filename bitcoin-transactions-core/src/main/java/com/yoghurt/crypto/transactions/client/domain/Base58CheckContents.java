package com.yoghurt.crypto.transactions.client.domain;

import java.io.Serializable;

public class Base58CheckContents implements Serializable {
  private static final long serialVersionUID = -8409900829487614335L;

  private byte version;
  private byte[] payload;
  private byte[] checksum;
  private byte[] fullChecksum;

  public Base58CheckContents() {}

  public byte getVersion() {
    return version;
  }

  public void setVersion(final byte version) {
    this.version = version;
  }

  public byte[] getPayload() {
    return payload;
  }

  public void setPayload(final byte[] payload) {
    this.payload = payload;
  }

  public byte[] getChecksum() {
    return checksum;
  }

  public void setChecksum(final byte[] checksum) {
    this.checksum = checksum;
  }

  public byte[] getFullChecksum() {
    return fullChecksum;
  }

  public void setFullChecksum(final byte[] fullChecksum) {
    this.fullChecksum = fullChecksum;
  }
}
