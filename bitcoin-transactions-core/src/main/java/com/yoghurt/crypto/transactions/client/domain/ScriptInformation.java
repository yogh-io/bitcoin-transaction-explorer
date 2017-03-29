package com.yoghurt.crypto.transactions.client.domain;

import com.yoghurt.crypto.transactions.shared.domain.TransactionOutPoint;

public class ScriptInformation {
  private TransactionOutPoint outpoint;
  private ScriptEntity pubKeySig;
  private ScriptEntity scriptSig;

  public ScriptEntity getPubKeySig() {
    return pubKeySig;
  }

  public void setPubKeySig(final ScriptEntity pubKeySig) {
    this.pubKeySig = pubKeySig;
  }

  public ScriptEntity getScriptSig() {
    return scriptSig;
  }

  public void setScriptSig(final ScriptEntity scriptSig) {
    this.scriptSig = scriptSig;
  }

  public TransactionOutPoint getOutpoint() {
    return outpoint;
  }

  public void setOutpoint(TransactionOutPoint outpoint) {
    this.outpoint = outpoint;
  }
}
