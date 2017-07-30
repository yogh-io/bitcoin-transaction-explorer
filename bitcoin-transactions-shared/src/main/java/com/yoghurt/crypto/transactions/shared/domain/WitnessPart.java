package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

public class WitnessPart implements Serializable {
  private VariableLengthInteger length;
  private byte[] data;

  public WitnessPart() {
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public VariableLengthInteger getLength() {
    return length;
  }

  public void setLength(VariableLengthInteger length) {
    this.length = length;
  }
}
