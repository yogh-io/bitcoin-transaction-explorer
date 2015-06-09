package com.yoghurt.crypto.transactions.shared.domain;

import java.util.Arrays;

public class TransactionOutPoint {
  private long index;
  private byte[] referenceTransaction;

  public byte[] getReferenceTransaction() {
    return referenceTransaction;
  }

  public void setReferenceTransaction(final byte[] referenceTransaction) {
    this.referenceTransaction = referenceTransaction;
  }

  public long getIndex() {
    return index;
  }

  public void setIndex(final long index) {
    this.index = index;
  }

  public boolean isCoinbase() {
    for (int i = 0; i < referenceTransaction.length; i++) {
      if (referenceTransaction[i] != 0) {
        return false;
      }
    }

    return true;
  }

  @Override
  public String toString() {
    return "TransactionOutPoint [index=" + index + "\nreferenceTransaction=" + Arrays.toString(referenceTransaction) + "]";
  }


}
