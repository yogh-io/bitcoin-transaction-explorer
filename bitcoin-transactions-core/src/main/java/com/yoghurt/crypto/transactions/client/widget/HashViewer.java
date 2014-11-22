package com.yoghurt.crypto.transactions.client.widget;

import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.client.util.misc.EllipsisUtil;

public class HashViewer extends ContextFieldSet<String> {
  /**
   * The number of characters to show before applying an inner ellipsis.
   */
  private static final int HASH_ELLIPSIS = 20;

  private final Color color;

  public HashViewer(final Color color) {
    super(new SimpleContextFactory<String>() {
      @Override
      protected String getTextValue(final String value) {
        return value.toUpperCase();
      }
    });

    this.color = color;
  }

  @Override
  protected Color getFieldColor(final String value) {
    return color;
  }

  @Override
  protected String getFieldText(final String value) {
    return EllipsisUtil.applyInner(value, HASH_ELLIPSIS);
  }

  public void setValue(final byte[] bytes) {
    addField(Str.toString(Hex.encode(bytes)).toUpperCase());
  }
}