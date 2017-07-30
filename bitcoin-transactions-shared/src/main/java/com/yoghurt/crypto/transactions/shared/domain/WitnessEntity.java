package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class WitnessEntity implements Serializable {
  private static final long serialVersionUID = 5099096292611529744L;

  private VariableLengthInteger witnessSize;
  private ArrayList<WitnessPart> pushdata = new ArrayList<WitnessPart>();

  public WitnessEntity() {
  }

  public VariableLengthInteger getScriptSize() {
    return witnessSize;
  }

  public void setScriptSize(final VariableLengthInteger scriptSize) {
    this.witnessSize = scriptSize;
  }

  public ArrayList<WitnessPart> getPushDatas() {
    return pushdata;
  }

  public void setPushDatas(final ArrayList<WitnessPart> pushdata) {
    this.pushdata = pushdata;
  }
}
