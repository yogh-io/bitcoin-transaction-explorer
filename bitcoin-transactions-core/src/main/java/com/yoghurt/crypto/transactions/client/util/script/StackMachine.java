package com.yoghurt.crypto.transactions.client.util.script;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import com.yoghurt.crypto.transactions.client.domain.ScriptExecutionPart;
import com.yoghurt.crypto.transactions.client.domain.ScriptInformation;
import com.yoghurt.crypto.transactions.client.domain.ScriptPart;
import com.yoghurt.crypto.transactions.client.domain.ScriptType;
import com.yoghurt.crypto.transactions.client.domain.StackObject;

public class StackMachine implements Iterable<ExecutionStep>, Iterator<ExecutionStep> {
  private final LinkedList<ScriptExecutionPart> script = new LinkedList<ScriptExecutionPart>();

  private final Deque<StackObject> stack = new LinkedList<StackObject>();

  private boolean executionError;

  public StackMachine(final ScriptInformation scriptInformation) {
    addInstructions(ScriptType.SCRIPT_SIG, scriptInformation.getScriptSig().getInstructions());
    addInstructions(ScriptType.SCRIPT_PUB_KEY, scriptInformation.getPubKeySig().getInstructions());
  }

  @Override
  public Iterator<ExecutionStep> iterator() {
    return this;
  }

  @Override
  public boolean hasNext() {
    return !script.isEmpty();
  }

  /**
   * TODO All state is lost on the next call to next(), if this is undesired then all state
   * needs to be copied into the execution step.
   */
  @Override
  public ExecutionStep next() {
    // Create object for the current execution step
    final ExecutionStep executionStep = new ExecutionStep();

    // Take the next instruction from the script, add it as the execution step's operation
    final ScriptExecutionPart pop = script.pop();
    executionStep.setOperation(pop);

    // Stick the remaining script in the execution step
    executionStep.setScript(script);

    // Stick the stack in the execution step
    executionStep.setStack(stack);

    // Execute this step
    ScriptExecutionUtil.execute(executionStep);

    setExecutionError(executionStep.hasExecutionError());

    return executionStep;
  }

  private void setExecutionError(final boolean hasExecutionError) {
    executionError = executionError || hasExecutionError;
  }

  @Override
  public void remove() {
    // No-op
  }

  private void addInstructions(final ScriptType origin, final ArrayList<ScriptPart> instructions) {
    for (final ScriptPart part : instructions) {
      script.add(new ScriptExecutionPart(origin, part));
    }
  }

  public LinkedList<ScriptExecutionPart> getScript() {
    return script;
  }

  public Deque<StackObject> getStack() {
    return stack;
  }

  public boolean hasExecutionError() {
    return executionError;
  }
}
