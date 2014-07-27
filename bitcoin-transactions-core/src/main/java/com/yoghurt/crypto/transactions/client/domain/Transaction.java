package com.yoghurt.crypto.transactions.client.domain;

import java.util.ArrayList;

import com.yoghurt.crypto.transactions.client.util.VariableInteger;

public class Transaction {
  private int version;
  private int lockTime;

  private VariableInteger inputSize;
  private ArrayList<TransactionInput> inputs;

  private VariableInteger outputSize;
  private ArrayList<TransactionOutput> outputs;


  public int getVersion() {
    return version;
  }

  public void setVersion(final int version) {
    this.version = version;
  }

  public VariableInteger getInputSize() {
    return inputSize;
  }

  public void setInputSize(final VariableInteger inputSize) {
    this.inputSize = inputSize;
  }

  public ArrayList<TransactionInput> getInputs() {
    return inputs;
  }

  public void setInputs(final ArrayList<TransactionInput> inputs) {
    this.inputs = inputs;
  }

  public VariableInteger getOutSize() {
    return outputSize;
  }

  public void setOutputSize(final VariableInteger outputSize) {
    this.outputSize = outputSize;
  }

  public ArrayList<TransactionOutput> getOutputs() {
    return outputs;
  }

  public void setOutputs(final ArrayList<TransactionOutput> outputs) {
    this.outputs = outputs;
  }

  public int getLockTime() {
    return lockTime;
  }

  public void setLockTime(final int lockTime) {
    this.lockTime = lockTime;
  }
}
