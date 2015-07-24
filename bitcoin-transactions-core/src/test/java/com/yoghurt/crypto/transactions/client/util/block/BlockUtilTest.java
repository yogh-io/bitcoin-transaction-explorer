package com.yoghurt.crypto.transactions.client.util.block;

import org.junit.Assert;
import org.junit.Test;

public class BlockUtilTest {
  @Test
  public void getDifficultyTargetTest() {

    final byte[] min = new byte[] { 0x1D & 0xFF, 0x00 & 0xFF, (byte) (0xFF & 0xFF), (byte) (0xFF & 0xFF)};
    final byte[] spec = new byte[] { 0x1B & 0xFF, 0x04 & 0xFF, (byte) (0x04 & 0xFF), (byte) (0xCB & 0xFF)};
    final byte[] dev = new byte[] { 0x18 & 0xFF, 0x1B & 0xFF, (byte) (0xC3 & 0xFF), (byte) (0x30 & 0xFF)};

    final byte[] minDiff = BlockUtil.getDifficultyTarget(min);
    final byte[] specDiff = BlockUtil.getDifficultyTarget(spec);
    final byte[] devDiff = BlockUtil.getDifficultyTarget(dev);

    Assert.assertEquals(0x00 & 0xFF, minDiff[0] & 0xFF);
    Assert.assertEquals(0xFF & 0xFF, minDiff[1] & 0xFF);
    Assert.assertEquals(0xFF & 0xFF, minDiff[2] & 0xFF);
    Assert.assertEquals(0x00 & 0xFF, minDiff[0] & 0xFF);
    Assert.assertEquals(min[0] & 0xFF, minDiff.length);
    Assert.assertEquals(spec[0] & 0xFF, specDiff.length);
    Assert.assertEquals(dev[0] & 0xFF, devDiff.length);
  }
}
