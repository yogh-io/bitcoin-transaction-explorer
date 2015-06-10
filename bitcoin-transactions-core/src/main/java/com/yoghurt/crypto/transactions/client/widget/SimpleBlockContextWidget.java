package com.yoghurt.crypto.transactions.client.widget;

import java.util.Map.Entry;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.widget.ContextFieldSet.FieldContextFactory;
import com.yoghurt.crypto.transactions.shared.domain.BlockPartType;

public class SimpleBlockContextWidget implements FieldContextFactory<Entry<BlockPartType, byte[]>> {
  @Override
  public Widget getContextWidget(final Entry<BlockPartType, byte[]> value) {
    final Label label = new Label(getTextForPart(value.getKey()));
    label.getElement().getStyle().setPadding(10, Unit.PX);
    return label;
  }

  private String getTextForPart(final BlockPartType type) {
    switch (type) {
    case VERSION:
      return "Block version number";
    case PREV_BLOCK_HASH:
      return "Previous block hash";
    case MERKLE_ROOT:
      return "Merkle root";
    case TIMESTAMP:
      return "Timestamp";
    case BITS:
      return "Bits value / federated consensus blocksigning";
    case NONCE:
      return "Nonce";
    default:
      return "Unknown block part.";
    }

  }
}
