package com.yoghurt.crypto.transactions.shared.domain;

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

  public boolean isCoinbase() {
    for (int i = 0; i < referenceTransaction.length; i++) {
      if (referenceTransaction[0] != 0) {
        return false;
      }
    }

    return true;
  }
}
