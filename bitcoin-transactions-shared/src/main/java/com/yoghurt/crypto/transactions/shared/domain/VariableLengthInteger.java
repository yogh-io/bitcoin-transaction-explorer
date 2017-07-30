package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;
import com.yoghurt.crypto.transactions.shared.util.NumberEncodeUtil;
import com.yoghurt.crypto.transactions.shared.util.NumberParseUtil;

public final class VariableLengthInteger implements Serializable {
  private static final long serialVersionUID = 3006944545057671762L;

  private long value;
  private int byteSize;

  private byte[] bytes;

  public VariableLengthInteger() {
  }

  public VariableLengthInteger(final byte[] bytes) {
    this(bytes, 0);
  }

  public VariableLengthInteger(final byte[] bytes, final int pointer) {
    final int lengthByte = bytes[pointer] & 0xFF;

    if (lengthByte <= 252) {
      value = lengthByte;
      byteSize = 1;
    } else if (lengthByte == 253) {
      value = NumberParseUtil.parseLong(ArrayUtil.arrayCopy(bytes, pointer + 1, pointer + 3));
      byteSize = 3;
    } else if (lengthByte == 254) {
      value = NumberParseUtil.parseLong(ArrayUtil.arrayCopy(bytes, pointer + 1, pointer + 5));
      byteSize = 5;
    } else { // Must be 255
      value = NumberParseUtil.parseLong(ArrayUtil.arrayCopy(bytes, pointer + 1, pointer + 9));
      byteSize = 9;
    }

    this.bytes = ArrayUtil.arrayCopy(bytes, pointer, pointer + byteSize);
  }

  public VariableLengthInteger(long value) {
    switch (sizeOf(value)) {
    case 1:
      bytes = new byte[] { (byte) value };
      break;
    case 3:
      bytes = new byte[] { (byte) 253, (byte) (value), (byte) (value >> 8) };
      break;
    case 5:
      bytes = new byte[5];
      bytes[0] = (byte) 254;
      byte[] encodeUint32 = NumberEncodeUtil.encodeUint32(value);
      System.arraycopy(encodeUint32, 0, bytes, 1, 4);
      break;
    default:
      bytes = new byte[9];
      bytes[0] = (byte) 255;
      byte[] encodeUint64 = NumberEncodeUtil.encodeUint64(value);
      System.arraycopy(encodeUint64, 0, bytes, 1, 8);
      break;
    }
  }

  /**
   * Returns the minimum encoded size of the given unsigned long value.
   *
   * @param value
   *          the unsigned long value (beware widening conversion of negatives!)
   */
  public static int sizeOf(long value) {
    // if negative, it's actually a very large unsigned long value
    if (value < 0)
      return 9; // 1 marker + 8 data bytes
    if (value < 253)
      return 1; // 1 data byte
    if (value <= 0xFFFFL)
      return 3; // 1 marker + 2 data bytes
    if (value <= 0xFFFFFFFFL)
      return 5; // 1 marker + 4 data bytes
    return 9; // 1 marker + 8 data bytes
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

  @SuppressWarnings("unused")
  private void setValue(final long value) {
    this.value = value;
  }

  public byte[] getBytes() {
    return bytes;
  }

  @SuppressWarnings("unused")
  private void setBytes(final byte[] bytes) {
    this.bytes = bytes;
  }
}