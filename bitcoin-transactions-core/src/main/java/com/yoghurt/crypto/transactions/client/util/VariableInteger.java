package com.yoghurt.crypto.transactions.client.util;


public final class VariableInteger {
  private final long value;
  private int byteSize;

  public VariableInteger(final byte[] bytes) {
    this(bytes, 0);
  }

  public VariableInteger(final byte[] bytes, final int pointer) {
    final int lengthByte = bytes[pointer] & 0xFF;

    if (lengthByte <= 252) {
      value = lengthByte;
      byteSize = 1;
    } else if (lengthByte == 253) {
      value = NumberParseUtil.parseLong(arrayCopy(bytes, pointer + 1, pointer + 3));
      byteSize = 3;
    } else if (lengthByte == 254) {
      value = NumberParseUtil.parseLong(arrayCopy(bytes, pointer + 1, pointer + 5));
      byteSize = 5;
    } else { // Must be 255
      value = NumberParseUtil.parseLong(arrayCopy(bytes, pointer + 1, pointer + 9));
      byteSize = 9;
    }
  }

  public int getByteSize() {
    return byteSize;
  }

  public void setByteSize(final int byteSize) {
    this.byteSize = byteSize;
  }

  public long getValue() {
    return value;
  }

  @Deprecated
  private static byte[] arrayCopy(final byte[] bytes, final int start, final int end) {
    final byte[] destBytes = new byte[end - start];

    System.arraycopy(bytes, start, destBytes, 0, end - start);

    return destBytes;
  }
}
