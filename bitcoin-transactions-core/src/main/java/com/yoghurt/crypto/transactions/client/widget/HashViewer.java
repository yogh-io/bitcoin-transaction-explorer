package com.yoghurt.crypto.transactions.client.widget;

import com.yoghurt.crypto.transactions.client.util.Color;
import com.yoghurt.crypto.transactions.client.util.EllipsisUtil;

public class HashViewer extends ContextFieldSet<String> {
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

  public void setValue(final String hash) {
    addField(hash.toUpperCase());
  }
}