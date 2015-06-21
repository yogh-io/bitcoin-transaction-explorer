package com.yoghurt.crypto.transactions.shared.domain;

public class StackObject {
  private byte[] bytes;

  public StackObject(final byte[] bytes) {
    this.bytes = bytes;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(final byte[] bytes) {
    this.bytes = bytes;
  }
}
