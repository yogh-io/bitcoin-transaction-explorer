package com.yoghurt.crypto.transactions.shared.domain;


public class TransactionOutput extends ScriptEntity {
  private long transactionValue;

  public long getTransactionValue() {
    return transactionValue;
  }

  public void setTransactionValue(final long transactionValue) {
    this.transactionValue = transactionValue;
  }
}
