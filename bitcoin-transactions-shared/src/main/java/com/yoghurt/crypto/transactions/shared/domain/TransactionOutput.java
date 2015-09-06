package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

public class TransactionOutput extends ScriptEntity implements Serializable {
  private static final long serialVersionUID = 4196375417196417138L;

  private long transactionValue;
  private int outputIndex;

  public TransactionOutput() {}

  public long getTransactionValue() {
    return transactionValue;
  }

  public void setTransactionValue(final long transactionValue) {
    this.transactionValue = transactionValue;
  }

  public int getOutputIndex() {
    return outputIndex;
  }

  public void setOutputIndex(final int outputIndex) {
    this.outputIndex = outputIndex;
  }
}
