package com.yoghurt.crypto.transactions.shared.domain;

public class StackObject {
  private byte[] data;

  public StackObject(final byte[] data) {
    this.data = data;
  }

  public byte[] getBytes() {
    return data;
  }

  public void setData(final byte[] data) {
    this.data = data;
  }
}
