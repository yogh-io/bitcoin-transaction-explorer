package com.yoghurt.crypto.transactions.client.util;

import org.junit.Assert;
import org.junit.Test;

public final class NumberEncodeUtilTest {
  private NumberEncodeUtilTest() {}

  @Test
  public void testEncodeUint32() {
    Assert.assertEquals(new byte[] { 1, 0, 0, 0 }, NumberEncodeUtil.encodeUint32(1));
  }

  @Test
  public void testEncodeUint64() {

  }
}
