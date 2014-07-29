package com.yoghurt.crypto.transactions.client.domain.transaction;

import com.yoghurt.crypto.transactions.client.util.VariableInteger;

public class TransactionOutput implements ScriptEntity {
  private long transactionValue;
  private VariableInteger scriptSize;
  private SignatureScript signatureScript;

  public long getTransactionValue() {
    return transactionValue;
  }

  public void setTransactionValue(final long transactionValue) {
    this.transactionValue = transactionValue;
  }

  @Override
  public VariableInteger getScriptSize() {
    return scriptSize;
  }

  @Override
  public void setScriptSize(final VariableInteger scriptSize) {
    this.scriptSize = scriptSize;
  }

  @Override
  public SignatureScript getSignatureScript() {
    return signatureScript;
  }

  @Override
  public void setSignatureScript(final SignatureScript signatureScript) {
    this.signatureScript = signatureScript;
  }
}
