package com.yoghurt.crypto.transactions.client.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ScriptEntity implements Serializable {
  private static final long serialVersionUID = -834280038838876590L;

  private long scriptSize;
  private ArrayList<ScriptPart> instructions = new ArrayList<ScriptPart>();

  public ScriptEntity() {}

  public long getScriptSize() {
    return scriptSize;
  }

  public void setScriptSize(final long scriptSize) {
    this.scriptSize = scriptSize;
  }

  public ArrayList<ScriptPart> getInstructions() {
    return instructions;
  }

  public void setInstructions(final ArrayList<ScriptPart> instructions) {
    this.instructions = instructions;
  }
}
