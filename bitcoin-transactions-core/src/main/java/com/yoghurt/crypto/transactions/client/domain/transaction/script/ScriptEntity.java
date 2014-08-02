package com.yoghurt.crypto.transactions.client.domain.transaction.script;

import java.util.ArrayList;

import com.yoghurt.crypto.transactions.client.util.VariableLengthInteger;

public class ScriptEntity {
  private VariableLengthInteger scriptSize;
  private final ArrayList<ScriptPart> instructions = new ArrayList<ScriptPart>();

  public VariableLengthInteger getScriptSize() {
    return scriptSize;
  }

  public void setScriptSize(final VariableLengthInteger scriptSize) {
    this.scriptSize = scriptSize;
  }

  public void addInstruction(final ScriptPart part) {
    instructions.add(part);
  }

  public ArrayList<ScriptPart> getInstructions() {
    return instructions;
  }
}
