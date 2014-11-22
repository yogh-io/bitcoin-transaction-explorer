package com.yoghurt.crypto.transactions.client.util;

import org.junit.Assert;
import org.junit.Test;

import com.yoghurt.crypto.transactions.shared.domain.VariableLengthInteger;


public class VariableIntegerTest {
  @Test
  public void testParseVarInt() {
    Assert.assertEquals(15, new VariableLengthInteger(new byte[] { 15 }).getValue());
    Assert.assertEquals(259, new VariableLengthInteger(new byte[] { (byte) 253, 3, 1 }).getValue());
    Assert.assertEquals(139, new VariableLengthInteger(new byte[] { (byte) 0x8B }).getValue());
  }
}
