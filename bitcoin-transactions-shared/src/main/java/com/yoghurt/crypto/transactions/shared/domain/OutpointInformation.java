package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

public class OutpointInformation implements Serializable {
  private static final long serialVersionUID = -3935787088468311231L;

  private int idx;
  private String referenceTransaction;
  private long amount;
  private boolean spent;

  public void setIndex(int idx) {
    this.idx = idx;
  }

  public void setReferenceTransaction(String referenceTransaction) {
    this.referenceTransaction = referenceTransaction;
  }

  public void setTransactionValue(long amount) {
    this.amount = amount;
  }

  public void setSpent(boolean spent) {
    this.spent = spent;
  }

  public String getReferenceTransaction() {
    return referenceTransaction;
  }

  public int getIndex() {
    return idx;
  }

  public double getTransactionValue() {
    return amount;
  }

  public boolean isSpent() {
    return spent;
  }
}
