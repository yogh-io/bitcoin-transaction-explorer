package com.yoghurt.crypto.transactions.shared.util;


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

  public static long parseUint32(final byte[] bytes) {
    return (bytes[0] & 0xFFL) << 0 |
        (bytes[1] & 0xFFL) << 8 |
        (bytes[2] & 0xFFL) << 16 |
        (bytes[3] & 0xFFL) << 24;
  }
}
