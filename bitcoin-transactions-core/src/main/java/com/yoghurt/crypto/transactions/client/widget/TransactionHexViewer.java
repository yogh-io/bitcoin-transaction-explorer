package com.yoghurt.crypto.transactions.client.widget;

import java.util.Map.Entry;

import com.yoghurt.crypto.transactions.client.domain.TransactionPartType;
import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.util.misc.Color;

public class TransactionHexViewer extends HexViewer<TransactionPartType> {
  public TransactionHexViewer() {
    super(new SimpleTransactionContextWidget());
  }

  @Override
  protected Color getFieldColor(final Entry<TransactionPartType, byte[]> value) {
    return TransactionPartColorPicker.getFieldColor(value.getKey());
  }
}
