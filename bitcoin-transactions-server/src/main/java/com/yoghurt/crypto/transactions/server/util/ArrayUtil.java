package com.yoghurt.crypto.transactions.server.util;

public final class ArrayUtil {
  private ArrayUtil() {}

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
}
