package com.yoghurt.crypto.transactions.client.util;

import org.junit.Assert;
import org.junit.Test;

import com.yoghurt.crypto.transactions.shared.util.NumberParseUtil;

public class NumberParseUtilTest {

  @Test
  public void testParseLong() {
    Assert.assertEquals(259, NumberParseUtil.parseLong(new byte[] { 3, 1 }));
  }
}
