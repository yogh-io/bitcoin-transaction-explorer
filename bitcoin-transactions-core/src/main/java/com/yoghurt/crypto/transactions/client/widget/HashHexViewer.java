package com.yoghurt.crypto.transactions.client.widget;

import com.yoghurt.crypto.transactions.client.util.BlockPartColorPicker;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.shared.domain.BlockPartType;


public class HashHexViewer extends HexViewer<Byte> {
  private static final Color COLOR = BlockPartColorPicker.getFieldColor(BlockPartType.PREV_BLOCK_HASH);

  public HashHexViewer() {
    super(new SimpleContextFactory<Byte>() {
      @Override
      protected String getTextValue(final Byte value) {
        return "Block hash";
      }
    });
  }

  public void setHash(final byte[] hash) {
    clear();

    for (final byte bite : hash) {
      addFields(bite);
    }
  }

  public void updateHash(final byte[] hash) {
    for(int i = 0; i < hash.length; i++) {
      final ContextField<Byte> contextField = fields.get(i);

      if(contextField == null) {
        continue;
      }

      contextField.setContent(getHexFromByte(hash[i]));
    }
  }

  @Override
  protected Color getFieldColor(final Byte value) {
    return COLOR;
  }

  @Override
  protected byte[] getBytesForValue(final Byte value) {
    return new byte[] { value };
  }
}
