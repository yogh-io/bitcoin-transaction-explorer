package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;
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
