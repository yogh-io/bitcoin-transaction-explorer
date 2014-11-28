package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

public class BlockInformation implements Serializable {
  private static final long serialVersionUID = -6695967392021472565L;

  private int height;

  public BlockInformation() {}

  public BlockInformation(final int height) {
    this.height = height;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(final int height) {
    this.height = height;
  }

}
