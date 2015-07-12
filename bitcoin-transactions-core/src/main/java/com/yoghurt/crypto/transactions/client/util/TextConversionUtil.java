package com.yoghurt.crypto.transactions.client.util;

public class TextConversionUtil {
  public static String fromASCIIBytes(final byte[] bytes) {
    final StringBuilder sb = new StringBuilder(bytes.length);
    for (int i = 0; i < bytes.length; ++i) {
      sb.append((char) bytes[i]);
    }
    return sb.toString();
  }
}
