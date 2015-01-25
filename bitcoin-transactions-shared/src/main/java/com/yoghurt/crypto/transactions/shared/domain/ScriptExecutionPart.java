package com.yoghurt.crypto.transactions.shared.domain;

public class ScriptExecutionPart extends ScriptPart {
  private final ScriptType origin;

  public ScriptExecutionPart(final ScriptType origin, final Operation operation) {
    super(operation);
    this.origin = origin;
  }

  public ScriptExecutionPart(final ScriptType origin, final Operation operation, final byte[] bytes) {
    super(operation, bytes);
    this.origin = origin;
  }

  public ScriptExecutionPart(final ScriptType origin, final ScriptPart original) {
    super(original.getOperation(), original.getBytes());
    this.origin = origin;
  }

  public ScriptType getOrigin() {
    return origin;
  }
}
