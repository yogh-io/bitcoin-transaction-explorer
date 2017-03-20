package com.yoghurt.crypto.transactions.client.domain;

import java.io.Serializable;

public class ScriptPart implements Serializable {
  private static final long serialVersionUID = 5588940226816723169L;

  private byte[] bytes;
  private Operation operation;

  public ScriptPart() {
  }

  public ScriptPart(final Operation operation) {
    this(operation, null);
  }

  public ScriptPart(final Operation operation, final byte[] bytes) {
    this.operation = operation;
    this.bytes = bytes;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public Operation getOperation() {
    return operation;
  }

  @SuppressWarnings("unused")
  private void setBytes(final byte[] bytes) {
    this.bytes = bytes;
  }

  @SuppressWarnings("unused")
  private void setOperation(final Operation operation) {
    this.operation = operation;
  }
}
