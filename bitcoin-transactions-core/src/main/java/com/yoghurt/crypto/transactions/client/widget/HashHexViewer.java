package com.yoghurt.crypto.transactions.client.widget;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;

import com.yoghurt.crypto.transactions.client.util.misc.Color;

public class HashHexViewer extends HexViewer<byte[]> {
  private Color color;

  public HashHexViewer() {
    super();
  }

  public HashHexViewer(final Color color) {
    this(color, null);
  }

  public HashHexViewer(final Color color, final FieldContextFactory<Entry<byte[], byte[]>> factory) {
    super(factory);

    this.color = color;
  }

  public void setHash(final byte[] hash) {
    if (hash == null || hash.length == 0 || color == null) {
      return;
    }

    final ArrayList<Entry<byte[], byte[]>> lst = new ArrayList<Entry<byte[], byte[]>>();

    for(final byte bite : hash) {
      lst.add(new AbstractMap.SimpleEntry<byte[], byte[]>(hash, new byte[] { bite }));
    }

    setContainer(lst);
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
