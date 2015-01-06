package com.yoghurt.crypto.transactions.shared.domain;

import java.util.Map.Entry;

public class RawTransactionPart implements Entry<TransactionPartType, byte[]>{
  private final TransactionPartType type;
  private byte[] bytes;

  public RawTransactionPart(final TransactionPartType type, final byte[] bytes) {
    this.type = type;
    this.bytes = bytes;
  }

  @Override
  public TransactionPartType getKey() {
    return type;
  }

  @Override
  public byte[] getValue() {
    return bytes;
  }

  @Override
  public byte[] setValue(final byte[] value) {
    return this.bytes = value;
  }
}
