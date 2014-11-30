package com.yoghurt.crypto.transactions.shared.util;

public final class NumberEncodeUtil {
  private NumberEncodeUtil() {}

  /**
   * TODO Figure out what's happening with negative values for int values (ie. if overflow is encoded correctly and it doesn't matter, in other words, whether providing an int value rather than a long is sufficient)
   */
  public static byte[] encodeUint32(final long value) {
    final byte[] bytes = new byte[4];

    bytes[0] = (byte) (value >> 0 & 0xFF);
    bytes[1] = (byte) (value >> 8 & 0xFF);
    bytes[2] = (byte) (value >> 16 & 0xFF);
    bytes[3] = (byte) (value >> 24 & 0xFF);

    return bytes;
  }

  public static byte[] encodeUint64(final long value) {
    final byte[] bytes = new byte[8];

    bytes[0] = (byte) (value >> 0 & 0xFF);
    bytes[1] = (byte) (value >> 8 & 0xFF);
    bytes[2] = (byte) (value >> 16 & 0xFF);
    bytes[3] = (byte) (value >> 24 & 0xFF);
    bytes[4] = (byte) (value >> 32 & 0xFF);
    bytes[5] = (byte) (value >> 40 & 0xFF);
    bytes[6] = (byte) (value >> 48 & 0xFF);
    bytes[7] = (byte) (value >> 56 & 0xFF);

    return bytes;
  }
}
