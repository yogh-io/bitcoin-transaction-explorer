package com.yoghurt.crypto.transactions.shared.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class Transaction {
  private byte[] txId;
  private byte[] witId;

  private long version;
  private long lockTime;

  private boolean isSegregatedWitness;

  private VariableLengthInteger inputSize;
  private ArrayList<TransactionInput> inputs;

  private VariableLengthInteger outputSize;
  private ArrayList<TransactionOutput> outputs;

  private byte witnessFlag;
  private VariableLengthInteger witnessScriptSize;

  private ArrayList<WitnessEntity> witnessScripts;


  public byte[] getTransactionId() {
    return txId;
  }

  public void setTransactionId(final byte[] txId) {
    this.txId = txId;
  }
  
  public byte[] getWitnessId() {
    return witId;
  }

  public void setWitnessId(byte[] witId) {
    this.witId = witId;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(final long version) {
    this.version = version;
  }

  public VariableLengthInteger getInputSize() {
    return inputSize;
  }

  public void setInputSize(final VariableLengthInteger inputSize) {
    this.inputSize = inputSize;
  }

  public ArrayList<TransactionInput> getInputs() {
    return inputs;
  }

  public void setInputs(final ArrayList<TransactionInput> inputs) {
    this.inputs = inputs;
  }

  public VariableLengthInteger getOutputSize() {
    return outputSize;
  }

  public void setOutputSize(final VariableLengthInteger outputSize) {
    this.outputSize = outputSize;
  }

  public ArrayList<TransactionOutput> getOutputs() {
    return outputs;
  }

  public void setOutputs(final ArrayList<TransactionOutput> outputs) {
    this.outputs = outputs;
  }

  public long getLockTime() {
    return lockTime;
  }

  public void setLockTime(final long lockTime) {
    this.lockTime = lockTime;
  }

  public boolean isCoinbase() {
    return !inputs.isEmpty() && inputs.get(0).isCoinbase();
  }

  public boolean isSegregatedWitness() {
    return isSegregatedWitness;
  }

  public void setSegregatedWitness(boolean isSegregatedWitness) {
    this.isSegregatedWitness = isSegregatedWitness;
  }

  public void setWitnessFlag(byte witnessFlag) {
    this.witnessFlag = witnessFlag;
  }

  public byte getWitnessFlag() {
    return witnessFlag;
  }

  public void setWitnessScriptSize(VariableLengthInteger witnessScriptSize) {
    this.witnessScriptSize = witnessScriptSize;
  }

  public VariableLengthInteger getWitnessScriptSize() {
    return witnessScriptSize;
  }

  public void setWitnessScripts(ArrayList<WitnessEntity> witnessScripts) {
    this.witnessScripts = witnessScripts;
  }

  public ArrayList<WitnessEntity> getWitnessScripts() {
    return witnessScripts;
  }

  @Override
  public String toString() {
    return "Transaction [txId=" + Arrays.toString(txId) + ", version=" + version + ", lockTime=" + lockTime + ", inputSize=" + inputSize + ", inputs="
        + inputs + ", outputSize=" + outputSize + ", outputs=" + outputs + "]";
  }
}
