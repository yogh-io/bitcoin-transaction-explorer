package com.yoghurt.crypto.transactions.shared.domain;

import java.util.ArrayList;
import java.util.Map.Entry;

public class RawTransactionContainer extends ArrayList<Entry<TransactionPartType, byte[]>> {
  private static final long serialVersionUID = 7581703223398068333L;

  private int byteSize = 0;

  public void add(final TransactionPartType type, final byte[] bytes) {
    add(new RawTransactionPart(type, bytes));
    byteSize += bytes.length;
  }

  @Override
  public void clear() {
    super.clear();
    byteSize = 0;
  }

  public byte[] getBytes() {
    final byte[] bytes = new byte[byteSize];

    int counter = 0;
    for (final Entry<TransactionPartType, byte[]> part : this) {
      final byte[] partBytes = part.getValue();
      System.arraycopy(partBytes, 0, bytes, counter, partBytes.length);
      counter += partBytes.length;
    }

    return bytes;
  }

  @Deprecated
  public Entry<TransactionPartType, byte[]> find(final TransactionPartType type) {
    for (final Entry<TransactionPartType, byte[]> part : this) {
      if (part.getKey() == type) {
        return part;
      }
    }

    return null;
  }
}
