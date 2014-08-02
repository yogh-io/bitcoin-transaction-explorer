package com.yoghurt.crypto.transactions.client.domain.transaction;

import com.yoghurt.crypto.transactions.client.domain.transaction.script.ScriptEntity;

public class TransactionOutput extends ScriptEntity {
  private long transactionValue;

  public long getTransactionValue() {
    return transactionValue;
  }

  public void setTransactionValue(final long transactionValue) {
    this.transactionValue = transactionValue;
  }
}
