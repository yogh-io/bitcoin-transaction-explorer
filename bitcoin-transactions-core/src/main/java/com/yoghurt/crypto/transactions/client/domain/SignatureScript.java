package com.yoghurt.crypto.transactions.client.domain;

public class SignatureScript {

  private byte[] scriptBytes;

  public void setScriptBytes(final byte[] scriptbytes) {
    scriptBytes = scriptbytes;
  }

  public byte[] getScriptBytes() {
    return scriptBytes;
  }
}
