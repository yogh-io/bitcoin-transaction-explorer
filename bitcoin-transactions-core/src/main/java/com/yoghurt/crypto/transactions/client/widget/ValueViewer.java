package com.yoghurt.crypto.transactions.client.widget;

import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.util.misc.Color;

public class ValueViewer extends ContextFieldSet<String> {
  private final Color color;

  protected String value;

  private boolean animate;

  public ValueViewer(final Color color) {
    this(color, new SimpleContextFactory<String>() {
      @Override
      protected String getTextValue(final String value) {
        return value;
      }
    });
  }

  public ValueViewer(final Color color, final String text) {
    super(text);

    this.color = color;
  }

  public ValueViewer(final Color color, final FieldContextFactory<String> contextFactory) {
    super(contextFactory);

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
    if(hash != null && hash.length != 0) {
      setValue(Str.toString(Hex.encode(hash)).toUpperCase());
    }
  }

  public void setValue(final Object obj) {
    setValue(String.valueOf(obj));
  }

  public void setValue(final String value) {
    this.value = value;

    if(fields.isEmpty()) {
      forceSetValue(value);
    } else {
      fields.get(0).setContent(value, animate);
    }
  }

  private void forceSetValue(final String value) {
    clear();
    addField(value);
  }

  public void setAnimate(final boolean animate) {
    this.animate = animate;
  }
}
