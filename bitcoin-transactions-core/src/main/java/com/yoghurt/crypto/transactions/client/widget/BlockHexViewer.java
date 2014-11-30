package com.yoghurt.crypto.transactions.client.widget;

import com.yoghurt.crypto.transactions.client.util.BlockPartColorPicker;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockPart;

public class BlockHexViewer extends HexViewer<RawBlockPart> {
  public BlockHexViewer() {
    super(new SimpleBlockContextWidget());
  }

  public void setBlock(final RawBlockContainer rawTransaction) {
    clear();

    for (final RawBlockPart part : rawTransaction) {
      addFields(part);
    }
  }

  @Override
  protected byte[] getBytesForValue(final RawBlockPart value) {
    return value.getBytes();
  }

  @Override
  protected Color getFieldColor(final RawBlockPart value) {
    return BlockPartColorPicker.getFieldColor(value.getType());
  }
}
