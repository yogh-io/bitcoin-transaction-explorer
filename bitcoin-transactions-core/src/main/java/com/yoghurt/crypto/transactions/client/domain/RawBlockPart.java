package com.yoghurt.crypto.transactions.client.domain;

public class RawBlockPart {
  private BlockPartType type;
  private byte[] bytes;

  public RawBlockPart(final BlockPartType type, final byte[] bytes) {
    this.type = type;
    this.bytes = bytes;
  }

  public BlockPartType getType() {
    return type;
  }

  public void setType(final BlockPartType type) {
    this.type = type;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(final byte[] bytes) {
    this.bytes = bytes;
  }
}
