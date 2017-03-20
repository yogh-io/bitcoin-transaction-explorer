package com.yoghurt.crypto.transactions.client.domain;

public class ScriptExecutionPart extends ScriptPart {
  private static final long serialVersionUID = -7301049146790848018L;

  private ScriptType origin;

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

  @SuppressWarnings("unused")
  private void setOrigin(final ScriptType origin) {
    this.origin = origin;
  }
}
