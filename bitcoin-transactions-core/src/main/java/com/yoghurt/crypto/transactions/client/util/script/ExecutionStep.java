package com.yoghurt.crypto.transactions.client.util.script;

import java.util.Deque;

import com.yoghurt.crypto.transactions.client.domain.ScriptExecutionPart;
import com.yoghurt.crypto.transactions.client.domain.StackObject;

public class ExecutionStep {
  private Deque<StackObject> stack;
  private Deque<ScriptExecutionPart> script;
  private ScriptExecutionPart scriptExecutionPart;
  private boolean executionError;

  public Deque<StackObject> getStack() {
    return stack;
  }

  public void setStack(final Deque<StackObject> stack) {
    this.stack = stack;
  }

  public Deque<ScriptExecutionPart> getScript() {
    return script;
  }

  public void setScript(final Deque<ScriptExecutionPart> script) {
    this.script = script;
  }

  public ScriptExecutionPart getInstruction() {
    return scriptExecutionPart;
  }

  public void setOperation(final ScriptExecutionPart scriptExecutionPart) {
    this.scriptExecutionPart = scriptExecutionPart;
  }

  public void setExecutionError(final boolean executionError) {
    this.executionError = executionError;
  }

  public boolean hasExecutionError() {
    return executionError;
  }
}
