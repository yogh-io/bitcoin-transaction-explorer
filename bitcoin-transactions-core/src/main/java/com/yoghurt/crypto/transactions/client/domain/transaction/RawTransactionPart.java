package com.yoghurt.crypto.transactions.client.domain.transaction;

public class RawTransactionPart {
  private TransactionPartType type;
  private byte[] bytes;

  public RawTransactionPart(final TransactionPartType type, final byte[] bytes) {
    this.type = type;
    this.bytes = bytes;
  }

  public TransactionPartType getType() {
    return type;
  }

  public void setType(final TransactionPartType type) {
    this.type = type;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(final byte[] bytes) {
    this.bytes = bytes;
  }
}
