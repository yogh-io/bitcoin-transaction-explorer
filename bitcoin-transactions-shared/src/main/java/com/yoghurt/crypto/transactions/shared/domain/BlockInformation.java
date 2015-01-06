package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

public class BlockInformation implements Serializable {
  private static final long serialVersionUID = -6695967392021472565L;

  private String rawBlockHeaders;

  private int height;
  private int numConfirmations;
  private int numTransactions;
  private long size;
  private String nextBlockHash;

  private String rawCoinbaseTransaction;
  private TransactionInformation coinbaseInformation;

  public BlockInformation() {}

  public String getRawBlockHeaders() {
    return rawBlockHeaders;
  }

  public void setRawBlockHeaders(final String rawBlockHeaders) {
    this.rawBlockHeaders = rawBlockHeaders;
  }

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

  public String getRawCoinbaseTransaction() {
    return rawCoinbaseTransaction;
  }

  public void setRawCoinbaseTransaction(final String rawCoinbaseTransaction) {
    this.rawCoinbaseTransaction = rawCoinbaseTransaction;
  }

  public TransactionInformation getCoinbaseInformation() {
    return coinbaseInformation;
  }

  public void setCoinbaseInformation(final TransactionInformation coinbaseInformation) {
    this.coinbaseInformation = coinbaseInformation;
  }
}
