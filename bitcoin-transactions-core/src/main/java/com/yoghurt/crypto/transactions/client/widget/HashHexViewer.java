package com.yoghurt.crypto.transactions.client.widget;

import java.util.AbstractMap;
import java.util.Map.Entry;

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

  public void setHash(final byte[] hash) {
    if(hash == null || hash.length == 0) {
      return;
    }

    if(fields.isEmpty()) {
      clear();

      for (final byte bite : hash) {
        addFields(new AbstractMap.SimpleEntry<Byte, byte[]>(new Byte(bite), new byte[] { bite }));
      }
    } else {
      for(int i = 0; i < hash.length; i++) {
        final ContextField<Entry<Byte, byte[]>> contextField = fields.get(i);

        if(contextField == null) {
          continue;
        }

        contextField.setContent(getHexFromByte(hash[i]));
      }
    }
  }

  @Override
  protected Color getFieldColor(final Entry<Byte, byte[]> value) {
    return color;
  }

  @Override
  protected byte[] getBytesForValue(final Entry<Byte, byte[]> value) {
    return value.getValue();
  }
}
