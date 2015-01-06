package com.yoghurt.crypto.transactions.shared.domain;

import java.util.ArrayList;
import java.util.Map.Entry;

public class RawTransactionContainer extends ArrayList<Entry<TransactionPartType, byte[]>> {
  private static final long serialVersionUID = 7581703223398068333L;

  public void add(final TransactionPartType type, final byte[] bytes) {
    add(new RawTransactionPart(type, bytes));
  }
}
