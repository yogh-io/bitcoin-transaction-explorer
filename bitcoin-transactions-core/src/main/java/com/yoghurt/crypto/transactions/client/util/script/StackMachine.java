package com.yoghurt.crypto.transactions.client.util.script;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import com.yoghurt.crypto.transactions.client.util.transaction.ScriptOperationUtil;
import com.yoghurt.crypto.transactions.shared.domain.ScriptExecutionPart;
import com.yoghurt.crypto.transactions.shared.domain.ScriptInformation;
import com.yoghurt.crypto.transactions.shared.domain.ScriptPart;
import com.yoghurt.crypto.transactions.shared.domain.ScriptType;
import com.yoghurt.crypto.transactions.shared.domain.StackObject;

public class StackMachine implements Iterable<StackState>, Iterator<StackState> {
  private final LinkedList<ScriptExecutionPart> script = new LinkedList<ScriptExecutionPart>();

  private final Deque<StackObject> stack = new LinkedList<StackObject>();

  public StackMachine(final ScriptInformation scriptInformation) {
    addInstructions(ScriptType.SCRIPT_SIG, scriptInformation.getScriptSig().getInstructions());
    addInstructions(ScriptType.SCRIPT_PUB_KEY, scriptInformation.getPubKeySig().getInstructions());
  }

  @Override
  public Iterator<StackState> iterator() {
    return this;
  }

  @Override
  public boolean hasNext() {
    return !script.isEmpty();
  }

  @Override
  public StackState next() {
    final StackState state = new StackState();
    state.setScript(script);

    final ScriptExecutionPart pop = script.pop();
    state.setOperation(pop);

    if(ScriptOperationUtil.isDataPushOperation(pop.getOperation())) {
      stack.push(new StackObject(pop.getBytes()));
    }

    state.setStack(stack);

    return state;
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
}
