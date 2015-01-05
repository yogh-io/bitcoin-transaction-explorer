package com.yoghurt.crypto.transactions.client.widget;

import java.util.Map.Entry;

import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;

public class TransactionHexViewer extends HexViewer<TransactionPartType> {
  public TransactionHexViewer() {
    super(new SimpleTransactionContextWidget());
  }

  //  @Override
  //  protected ArrayList<ContextField<Entry<TransactionPartType, byte[]>>> findValueFields(final Entry<TransactionPartType, byte[]> value) {
  //    for (final Entry<Entry<TransactionPartType, byte[]>, ArrayList<ContextField<Entry<TransactionPartType, byte[]>>>> field : fieldMap) {
  //      if (field.getKey().getKey() == value.getKey()) {
  //        return field.getValue();
  //      }
  //    }
  //
  //    return null;
  //  }

  @Override
  protected byte[] getBytesForValue(final Entry<TransactionPartType, byte[]> value) {
    return value.getValue();
  }

  @Override
  protected Color getFieldColor(final Entry<TransactionPartType, byte[]> value) {
    return TransactionPartColorPicker.getFieldColor(value.getKey());
  }
}
