package com.yoghurt.crypto.transactions.shared.domain;

public class ScriptPart {
  private final byte[] bytes;
  private final Operation opcode;

  public ScriptPart(final Operation opcode) {
    this(opcode, null);
  }

  public ScriptPart(final Operation opcode, final byte[] bytes) {
    this.opcode = opcode;
    this.bytes = bytes;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public Operation getOperation() {
    return opcode;
  }
}
