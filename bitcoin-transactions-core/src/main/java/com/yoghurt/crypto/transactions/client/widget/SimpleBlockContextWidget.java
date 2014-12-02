package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.widget.ContextFieldSet.FieldContextFactory;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockPart;

public class SimpleBlockContextWidget implements FieldContextFactory<RawBlockPart> {
  @Override
  public Widget getContextWidget(final RawBlockPart value) {
    final Label label = new Label(getTextForPart(value));
    label.getElement().getStyle().setPadding(10, Unit.PX);
    return label;
  }

  private String getTextForPart(final RawBlockPart value) {
    switch (value.getType()) {
    case VERSION:
      return "Block version number";
    case PREV_BLOCK_HASH:
      return "Previous block hash";
    case MERKLE_ROOT:
      return "Merkle root";
    case TIMESTAMP:
      return "Timestamp";
    case BITS:
      return "Bits value / difficulty target";
    case NONCE:
      return "Nonce";
    default:
      return "Unknown block part.";
    }

  }
}
