package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

public class AddressOutpoint extends TransactionOutPoint implements Serializable {
  private static final long serialVersionUID = -2104537209099839858L;

  private TransactionOutput output;

  public AddressOutpoint() {}

  public TransactionOutput getOutput() {
    return output;
  }

  public void setOutput(final TransactionOutput output) {
    this.output = output;
  }
}
