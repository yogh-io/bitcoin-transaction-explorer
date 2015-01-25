package com.yoghurt.crypto.transactions.shared.domain;

public class ScriptPart {
  private final byte[] bytes;
  private final Operation operation;

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
}
