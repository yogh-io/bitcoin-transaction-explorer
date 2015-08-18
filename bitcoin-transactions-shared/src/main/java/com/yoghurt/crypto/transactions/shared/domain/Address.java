package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

public class Address implements Serializable {
  private static final long serialVersionUID = -2343721099182394903L;

  /**
   * Heuristic pointer.
   */
  public enum TYPE {
    P2PKH, P2SH;
  }

  /**
   * Only set if explicit type is known.
   */
  private TYPE type;

  private byte version;
  private byte[] hash160;

  public Address() {
  }

  public byte[] getHash160() {
    return hash160;
  }

  public void setHash160(final byte[] hash160) {
    this.hash160 = hash160;
  }

  public TYPE getType() {
    return type;
  }

  public void setType(final TYPE type) {
    this.type = type;
  }

  public byte getVersion() {
    return version;
  }

  public void setVersion(byte version) {
    this.version = version;
  }
}
