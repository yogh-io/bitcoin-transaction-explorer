package com.yoghurt.crypto.transactions.client.widget;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwt.crypto.bouncycastle.util.Arrays;
import com.yoghurt.crypto.transactions.client.util.BlockPartColorPicker;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.shared.domain.BlockPartType;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;

public class BlockHexViewer extends HexViewer<Entry<BlockPartType, byte[]>> {
  public BlockHexViewer() {
    super(new SimpleBlockContextWidget());
  }

  public void setBlock(final RawBlockContainer rawBlock) {
    // If the container is empty, set instead of update
    if (fieldMap.isEmpty()) {
      clear();

      for (final Entry<BlockPartType, byte[]> part : rawBlock.entrySet()) {
        addFields(part);
      }
    }

    for (final Entry<BlockPartType, byte[]> entry : rawBlock.entrySet()) {
      final ArrayList<ContextField<Entry<BlockPartType, byte[]>>> field = findValueFields(entry);

      // Field shouldn't be empty, but check for it anyway
      if (field.isEmpty()) {
        continue;
      }

      final byte[] bytesForValue = getBytesForValue(entry);

      // If the value is equal, keep going
      if (Arrays.areEqual(bytesForValue, field.get(0).getValue().getValue())) {
        continue;
      }

      // Fields should be of equal size, but check for it anyway
      if (bytesForValue.length != field.size()) {
        GWT.log("Size inconsistency");
        // TODO Log and bug out.
        continue;
      }

      // Change the value for each field
      int i = 0;
      for (final byte bite : bytesForValue) {
        final ContextField<Entry<BlockPartType, byte[]>> contextField = field.get(i++);
        contextField.setContent(getHexFromByte(bite));

        // Disgusting...
        contextField.getValue().setValue(entry.getValue());
      }
    }
  }

  @Override
  protected ArrayList<ContextField<Entry<BlockPartType, byte[]>>> findValueFields(final Entry<BlockPartType, byte[]> value) {
    for (final Entry<Entry<BlockPartType, byte[]>, ArrayList<ContextField<Entry<BlockPartType, byte[]>>>> field : fieldMap) {
      if (field.getKey().getKey() == value.getKey()) {
        return field.getValue();
      }
    }

    return null;
  }

  @Override
  protected byte[] getBytesForValue(final Entry<BlockPartType, byte[]> value) {
    return value.getValue();
  }

  @Override
  protected Color getFieldColor(final Entry<BlockPartType, byte[]> value) {
    return BlockPartColorPicker.getFieldColor(value.getKey());
  }
}
