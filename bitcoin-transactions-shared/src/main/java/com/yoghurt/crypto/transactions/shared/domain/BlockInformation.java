package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

public class BlockInformation implements Serializable {
  private static final long serialVersionUID = -6695967392021472565L;

  private int height;
  private int numConfirmations;
  private int numTransactions;
  private long size;
  private String nextBlockHash;

  public BlockInformation() {}

  public long getSize() {
    return size;
  }

  public void setSize(final long size) {
    this.size = size;
  }

  public String getNextBlockHash() {
    return nextBlockHash;
  }

  public void setNextBlockHash(final String nextBlockHash) {
    this.nextBlockHash = nextBlockHash;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(final int height) {
    this.height = height;
  }

  public void setNumConfirmations(final int numConfirmations) {
    this.numConfirmations = numConfirmations;
  }

  public int getNumConfirmations() {
    return numConfirmations;
  }

  public int getNumTransactions() {
    return numTransactions;
  }

  public void setNumTransactions(final int numTransactions) {
    this.numTransactions = numTransactions;
  }
}
