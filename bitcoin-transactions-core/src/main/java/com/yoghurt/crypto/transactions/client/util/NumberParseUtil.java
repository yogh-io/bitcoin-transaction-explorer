package com.yoghurt.crypto.transactions.client.util;


public final class NumberParseUtil {
  private NumberParseUtil() {}

  public static long parseLong(final byte[] bytes) {
    return parseLong(bytes, 0, 0);
  }

  public static long parseLong(final byte[] bytes, final int offset) {
    return parseLong(bytes, offset, 0);
  }

  public static long parseLong(final byte[] bytes, final int offset, final int shift) {
    return bytes.length == offset ? 0 : bytes[offset] << shift | parseLong(bytes, offset + 1, shift + 8);
  }
}
