package com.yoghurt.crypto.transactions.client.widget;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;

import com.yoghurt.crypto.transactions.client.i18n.M;
import com.yoghurt.crypto.transactions.client.util.block.BlockUtil;
import com.yoghurt.crypto.transactions.client.util.misc.Color;

public class BitsTargetHexViewer extends HexViewer<byte[]> {
  private Color color;

  public static enum TYPE {
    /**
     * Pool difficulty, non-truncated; 0xFF after first 3 byte
     */
    PDIFF,

    /**
     * Actual difficulty, truncated; 0x00 after first 3 byte
     */
    BDIFF;
  }

  public BitsTargetHexViewer() {
    this(null);
  }

  public BitsTargetHexViewer(final Color color) {
    this(color, new TextContextFactory<Entry<byte[], byte[]>>(M.messages().blockBitsTargetExplain()));
  }

  public BitsTargetHexViewer(final Color color, final FieldContextFactory<Entry<byte[], byte[]>> factory) {
    super(factory);

    this.color = color;
  }

  public void setBits(final byte[] bits) {
    setBits(bits, TYPE.PDIFF);
  }

  public void setBits(final byte[] bits, final TYPE type) {
    if(bits == null || bits.length != BlockUtil.BITS_MANTISSA_LENGTH + 1) {
      return;
    }

    final byte[] target;

    switch(type) {
    case BDIFF:
      target = BlockUtil.getBitcoinDiffTarget(bits);
      break;
    default:
    case PDIFF:
      target = BlockUtil.getPoolDiffTarget(bits);
      break;
    }

    final ArrayList<Entry<byte[], byte[]>> lst = new ArrayList<Entry<byte[], byte[]>>();

    for(final byte bite : target) {
      lst.add(new AbstractMap.SimpleEntry<byte[], byte[]>(target, new byte[] { bite }));
    }

    setValue(lst);
  }

  @Override
  protected Color getFieldColor(final Entry<byte[], byte[]> value) {
    return color;
  }

  @Override
  protected byte[] getBytesForValue(final Entry<byte[], byte[]> value) {
    return value.getValue();
  }

  public void setColor(final Color color) {
    this.color = color;
  }
}
