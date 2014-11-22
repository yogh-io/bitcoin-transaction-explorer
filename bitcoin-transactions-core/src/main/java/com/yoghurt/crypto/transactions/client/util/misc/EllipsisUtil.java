package com.yoghurt.crypto.transactions.client.util.misc;

public final class EllipsisUtil {
  /**
   * Number of dots
   */
  private static final int ELLIPSIS_SIZE = 3;
  private static final int DEFAULT_SIZE = 10;

  private static final String ELLIPSIS = "...";

  private EllipsisUtil() {}

  /**
   * Apply an inner ellipsis to the given string
   * 
   * 
   * @return 'abcdefghijklmnopqrstuvw' -> 'abcde...stuvw'
   */
  public static String applyInner(final String str) {
    return applyInner(str, DEFAULT_SIZE);
  }

  public static String applyInner(final String str, final int size) {
    if (str.length() < size + ELLIPSIS_SIZE) {
      return str;
    } else {
      final int begin = (int) Math.ceil(size / 2d);
      final int end = (int) (size / 2d);

      return str.substring(0, begin) + ELLIPSIS + str.substring(str.length() - end, str.length());
    }
  }
}
