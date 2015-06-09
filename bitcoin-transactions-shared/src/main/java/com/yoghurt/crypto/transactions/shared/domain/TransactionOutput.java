package com.yoghurt.crypto.transactions.shared.domain;


public class TransactionOutput extends ScriptEntity {
  private byte[] serValue;
  private int outputIndex;

  public byte[] getTransactionValue() {
    return serValue;
  }

  public void setTransactionValue(final byte[] serValue) {
    this.serValue = serValue;
  }

  public int getOutputIndex() {
    return outputIndex;
  }

  public void setOutputIndex(int outputIndex) {
    this.outputIndex = outputIndex;
  }
}
