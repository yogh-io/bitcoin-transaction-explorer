package com.yoghurt.crypto.transactions.client.util;

import org.junit.Assert;
import org.junit.Test;


public class VariableIntegerTest {
  @Test
  public void testParseVarInt() {
    Assert.assertEquals(15, new VariableInteger(new byte[] { 15 }).getValue());
    Assert.assertEquals(259, new VariableInteger(new byte[] { (byte) 253, 3, 1 }).getValue());
    Assert.assertEquals(139, new VariableInteger(new byte[] { (byte) 0x8B }).getValue());
  }
}
