package com.yoghurt.crypto.transactions.shared.util;

public final class ArrayUtil {
  private ArrayUtil() {}

  public static byte[] arrayCopy(final byte[] bytes, final int start, final int end) {
    final byte[] destBytes = new byte[end - start];

    System.arraycopy(bytes, start, destBytes, 0, end - start);

    return destBytes;
  }

  public static void reverse(final byte[] array) {
    if (array == null) {
      return;
    }

    int i = 0;
    int j = array.length - 1;
    byte tmp;

    while (j > i) {
      tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      j--;
      i++;
    }
  }

  public static byte[] arrayCopy(final byte[] bytes) {
    return arrayCopy(bytes, 0, bytes.length);
  }
}
