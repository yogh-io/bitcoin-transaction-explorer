package com.yoghurt.crypto.transactions.client.util;

public final class ArrayUtil {
  private ArrayUtil() {}

  public static byte[] arrayCopy(final byte[] bytes, final int start, final int end) {
    final byte[] destBytes = new byte[end - start];

    System.arraycopy(bytes, start, destBytes, 0, end - start);

    return destBytes;
  }
}
