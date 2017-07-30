package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class WitnessEntity implements Serializable {
  private static final long serialVersionUID = 5099096292611529744L;

  private VariableLengthInteger itemsLength;
  private ArrayList<WitnessPart> pushdata = new ArrayList<WitnessPart>();

  public WitnessEntity() {
  }

  public VariableLengthInteger getItemsLength() {
    return itemsLength;
  }

  public void setItemsLength(final VariableLengthInteger scriptSize) {
    this.itemsLength = scriptSize;
  }

  public ArrayList<WitnessPart> getPushDatas() {
    return pushdata;
  }

  public void setPushDatas(final ArrayList<WitnessPart> pushdata) {
    this.pushdata = pushdata;
  }
}
