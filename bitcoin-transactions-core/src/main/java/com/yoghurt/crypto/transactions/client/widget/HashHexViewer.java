package com.yoghurt.crypto.transactions.client.widget;

import java.util.ArrayList;

import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.client.util.misc.ColorBuilder;


public class HashHexViewer extends HexViewer<Byte> {
  private final Color color;

  public HashHexViewer() {
    this(ColorBuilder.interpret("green"));
  }

  public HashHexViewer(final Color color) {
    super(null);

    this.color = color;
  }

  @Override
  protected ArrayList<ContextField<Byte>> findValueFields(final Byte value) {
    return null;
  }

  public void setHash(final byte[] hash) {
    if(hash == null || hash.length == 0) {
      return;
    }

    if(fieldMap.isEmpty()) {
      clear();

      for (final byte bite : hash) {
        addFields(bite);
      }
    } else {
      for(int i = 0; i < hash.length; i++) {
        final ContextField<Byte> contextField = fields.get(i);

        if(contextField == null) {
          continue;
        }

        contextField.setContent(getHexFromByte(hash[i]));
      }
    }
  }

  @Override
  protected Color getFieldColor(final Byte value) {
    return color;
  }

  @Override
  protected byte[] getBytesForValue(final Byte value) {
    return new byte[] { value };
  }
}
