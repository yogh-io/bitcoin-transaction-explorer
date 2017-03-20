package com.yoghurt.crypto.transactions.shared.service.domain;

import java.io.Serializable;

public class OutpointInformation implements Serializable {
  private static final long serialVersionUID = -3935787088468311231L;

  private int idx;
  private byte[] referenceTransaction;
  private long amount;
  private byte[] script;
  private boolean spent;

  public void setIndex(int idx) {
    this.idx = idx;
  }

  public void setReferenceTransaction(byte[] referenceTransaction) {
    this.referenceTransaction = referenceTransaction;
  }

  public void setTransactionValue(long amount) {
    this.amount = amount;
  }

  public void setOutputScript(byte[] script) {
    this.script = script;
  }

  public void setSpent(boolean spent) {
    this.spent = spent;
  }

  public byte[] getReferenceTransaction() {
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
