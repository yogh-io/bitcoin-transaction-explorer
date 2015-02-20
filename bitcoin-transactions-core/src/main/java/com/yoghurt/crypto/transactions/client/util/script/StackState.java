package com.yoghurt.crypto.transactions.client.util.script;

import java.util.Deque;

import com.yoghurt.crypto.transactions.shared.domain.ScriptExecutionPart;
import com.yoghurt.crypto.transactions.shared.domain.StackObject;

public class StackState {
  private Deque<StackObject> stack;
  private Deque<ScriptExecutionPart> script;
  private ScriptExecutionPart scriptExecutionPart;

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

  public ScriptExecutionPart getOperation() {
    return scriptExecutionPart;
  }

  public void setOperation(final ScriptExecutionPart scriptExecutionPart) {
    this.scriptExecutionPart = scriptExecutionPart;
  }
}
