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
      return "The hash of the block this block is built on top of";
    case MERKLE_ROOT:
      return "The merkle root, the hash of all transactions combined";
    case TIMESTAMP:
      return "The timestamp";
    case BITS:
      return "The bits value for this block";
    case NONCE:
      return "The nonce for this block";
    default:
      return "Unknown block part.";
    }

  }
}
