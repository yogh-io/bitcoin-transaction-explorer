package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class AddressInformation implements Serializable {
  private static final long serialVersionUID = -221605929040046034L;

  private ArrayList<AddressOutpoint> outpoints;

  public void setOutpoints(final ArrayList<AddressOutpoint> outpoints) {
    this.outpoints = outpoints;
  }

  public ArrayList<AddressOutpoint> getOutpoints() {
    return outpoints;
  }

}
