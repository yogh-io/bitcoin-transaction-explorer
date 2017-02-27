package com.yoghurt.crypto.transactions.shared.service.domain;

import java.io.Serializable;

public class TransactionOutPoint implements Serializable {
  private static final long serialVersionUID = -4673465272462836094L;

  private long index;
  private byte[] referenceTransaction;

  public TransactionOutPoint() {}

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
}
