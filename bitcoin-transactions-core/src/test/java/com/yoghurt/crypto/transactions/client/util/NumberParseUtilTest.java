package com.yoghurt.crypto.transactions.client.util;

import org.junit.Assert;
import org.junit.Test;

import com.yoghurt.crypto.transactions.shared.util.NumberParseUtil;

public class NumberParseUtilTest {

  @Test
  public void testParseLong() {
    Assert.assertEquals(259, NumberParseUtil.parseLong(new byte[] { 3, 1 }));
  }

  @Test
  public void testOneCounter() {
    Assert.assertEquals(3, NumberParseUtil.countOnes(7));
    Assert.assertEquals(1, NumberParseUtil.countOnes(8));
    Assert.assertEquals(1, NumberParseUtil.countOnes(4));
    Assert.assertEquals(1, NumberParseUtil.countOnes(2));
    Assert.assertEquals(1, NumberParseUtil.countOnes(1));
    Assert.assertEquals(8, NumberParseUtil.countOnes(255));
    Assert.assertNotEquals(1, NumberParseUtil.countOnes(255));
  }
}
