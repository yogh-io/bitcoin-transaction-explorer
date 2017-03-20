package com.yoghurt.crypto.transactions.shared.service.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class AddressInformation implements Serializable {
  private static final long serialVersionUID = -221605929040046034L;

  private ArrayList<OutpointInformation> outpoints;

  public void setOutpoints(final ArrayList<OutpointInformation> outpoints) {
    this.outpoints = outpoints;
  }

  public ArrayList<OutpointInformation> getOutpoints() {
    return outpoints;
  }

}
