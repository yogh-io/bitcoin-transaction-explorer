package com.yoghurt.crypto.transactions.client.util;

import org.junit.Assert;
import org.junit.Test;


public class VariableIntegerTest {
  @Test
  public void testParseVarInt() {
    Assert.assertEquals(15, new VariableLengthInteger(new byte[] { 15 }).getValue());
    Assert.assertEquals(259, new VariableLengthInteger(new byte[] { (byte) 253, 3, 1 }).getValue());
    Assert.assertEquals(139, new VariableLengthInteger(new byte[] { (byte) 0x8B }).getValue());
  }

  @Test
  public void testEncodeVarInt() {
    Assert.assertArrayEquals(new byte[] { 15 }, new VariableLengthInteger(15).getBytes());
    Assert.assertArrayEquals(new byte[] { (byte) 253, 3, 1 }, new VariableLengthInteger(259).getBytes());
    Assert.assertArrayEquals(new byte[] { (byte) 0x8B }, new VariableLengthInteger(139).getBytes());
  }

  @Test
  public void testVarIntByteSizes() {
    Assert.assertEquals(1, VariableLengthInteger.sizeOf(15));
  }
}
