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
    return bytes.length == offset ? 0 : (bytes[offset] & 0xFFL) << shift | parseLong(bytes, offset + 1, shift + 8);
  }

  public static int parseUint32(final byte[] bytes) {
    return bytes[0] & 0xFF
        | (bytes[1] & 0xFF) << 8
        | (bytes[2] & 0xFF) << 16
        | bytes[3] & 0xFF << 24;
  }
}
