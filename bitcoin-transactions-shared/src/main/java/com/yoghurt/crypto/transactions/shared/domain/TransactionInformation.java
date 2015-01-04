package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class TransactionInformation implements Serializable {
  private static final long serialVersionUID = -5230934399747974590L;

  private TransactionState state;
  private Date time;
  private int confirmations;
  private String blockHash;

  public TransactionInformation() {}

  public TransactionState getState() {
    return state;
  }

  public void setState(final TransactionState state) {
    this.state = state;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(final Date time) {
    this.time = time;
  }

  public int getConfirmations() {
    return confirmations;
  }

  public void setConfirmations(final int confirmations) {
    this.confirmations = confirmations;
  }

  public void setBlockHash(final String blockHash) {
    this.blockHash = blockHash;
  }

  public String getBlockHash() {
    return blockHash;
  }

  @Override
  public String toString() {
    return "TransactionInformation [state=" + state + ", block=" + blockHash + ", time=" + time + ", confirmations=" + confirmations + "]";
  }
}
