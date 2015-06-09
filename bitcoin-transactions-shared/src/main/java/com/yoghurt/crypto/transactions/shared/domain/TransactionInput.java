package com.yoghurt.crypto.transactions.shared.domain;


public class TransactionInput extends ScriptEntity  {
  private TransactionOutPoint outPoint;
  private long inputIndex;
  private long transactionSequence;

  public void setOutPoint(final TransactionOutPoint outPoint) {
    this.outPoint = outPoint;
  }

  public TransactionOutPoint getOutPoint() {
    return outPoint;
  }

  public long getTransactionSequence() {
    return transactionSequence;
  }

  public void setTransactionSequence(final long transactionSequence) {
    this.transactionSequence = transactionSequence;
  }

  public long getInputIndex() {
    return inputIndex;
  }

  public void setInputIndex(final long inputIndex) {
    this.inputIndex = inputIndex;
  }

  public boolean isCoinbase() {
    return outPoint != null && outPoint.isCoinbase();
  }

  @Override
  public String toString() {
    return "TransactionInput [outPoint=" + outPoint + "\ninputIndex=" + inputIndex + "\ntransactionSequence=" + transactionSequence + "]";
  }


}
