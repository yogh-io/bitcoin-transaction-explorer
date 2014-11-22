package com.yoghurt.crypto.transactions.shared.domain;


public class TransactionInput extends ScriptEntity  {
  private TransactionOutPoint outPoint;
  private int transactionSequence;

  public void setOutPoint(final TransactionOutPoint outPoint) {
    this.outPoint = outPoint;
  }

  public TransactionOutPoint getOutPoint() {
    return outPoint;
  }

  public int getTransactionSequence() {
    return transactionSequence;
  }

  public void setTransactionSequence(final int transactionSequence) {
    this.transactionSequence = transactionSequence;
  }
}
