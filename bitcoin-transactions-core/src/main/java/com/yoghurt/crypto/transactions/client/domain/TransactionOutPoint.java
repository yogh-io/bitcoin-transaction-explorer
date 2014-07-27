package com.yoghurt.crypto.transactions.client.domain;

public class TransactionOutPoint {
  private int index;
  private byte[] referenceTransaction;

  public byte[] getReferenceTransaction() {
    return referenceTransaction;
  }

  public void setReferenceTransaction(final byte[] referenceTransaction) {
    this.referenceTransaction = referenceTransaction;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(final int index) {
    this.index = index;
  }
}
