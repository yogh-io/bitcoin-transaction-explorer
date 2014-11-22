package com.yoghurt.crypto.transactions.client.widget;

import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.util.misc.Color;

public class ValueViewer extends ContextFieldSet<String> {

  private final Color color;

  public ValueViewer(final Color color) {
    super(new SimpleContextFactory<String>() {
      @Override
      protected String getTextValue(final String value) {
        return value;
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
    return value;
  }

  public void setValue(final byte[] hash) {
    setValue(Str.toString(Hex.encode(hash)).toUpperCase());
  }

  public void setValue(final String value) {
    clear();
    addField(value);
  }
}
