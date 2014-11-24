package com.yoghurt.crypto.transactions.shared.domain;


public class TransactionOutput extends ScriptEntity {
  private long transactionValue;
  private int outputIndex;

  public long getTransactionValue() {
    return transactionValue;
  }

  public void setTransactionValue(final long transactionValue) {
    this.transactionValue = transactionValue;
  }

  public int getOutputIndex() {
    return outputIndex;
  }

  public void setOutputIndex(int outputIndex) {
    this.outputIndex = outputIndex;
  }
}
