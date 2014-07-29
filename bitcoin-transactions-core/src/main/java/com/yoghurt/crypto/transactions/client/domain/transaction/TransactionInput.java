package com.yoghurt.crypto.transactions.client.domain.transaction;

import com.yoghurt.crypto.transactions.client.util.VariableInteger;

public class TransactionInput implements ScriptEntity  {
  private TransactionOutPoint outPoint;
  private VariableInteger scriptSize;
  private SignatureScript signatureScript;
  private int transactionSequence;

  public void setOutPoint(final TransactionOutPoint outPoint) {
    this.outPoint = outPoint;
  }

  public TransactionOutPoint getOutPoint() {
    return outPoint;
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

  public int getTransactionSequence() {
    return transactionSequence;
  }

  public void setTransactionSequence(final int transactionSequence) {
    this.transactionSequence = transactionSequence;
  }
}
