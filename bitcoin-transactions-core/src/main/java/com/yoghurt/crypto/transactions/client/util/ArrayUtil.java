package com.yoghurt.crypto.transactions.client.util;

public final class ArrayUtil {
  private ArrayUtil() {}

  public static byte[] arrayCopy(final byte[] bytes, final int start, final int end) {
    final byte[] destBytes = new byte[end - start];

    System.arraycopy(bytes, start, destBytes, 0, end - start);

    return destBytes;
  }

  public static byte[] reverseCopy(final byte[] src) {
    final byte[] arrayCopy = arrayCopy(src);

    reverse(arrayCopy);

    return arrayCopy;
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

  public static String[] copyFrom(final String[] origin, final int i) {
    final String[] dest = new String[origin.length - i];
    System.arraycopy(origin, i, dest, 0, origin.length - i);
    return dest;
  }

  public static byte[] combine(final byte[] a, final byte[] b) {
    final byte[] c = new byte[a.length + b.length];
    System.arraycopy(a, 0, c, 0, a.length);
    System.arraycopy(b, 0, c, a.length, b.length);

    return c;
  }
}
