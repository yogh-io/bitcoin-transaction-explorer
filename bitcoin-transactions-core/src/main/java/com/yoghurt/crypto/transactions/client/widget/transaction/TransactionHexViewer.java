package com.yoghurt.crypto.transactions.client.widget.transaction;

import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.client.widget.HexViewer;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionPart;

public class TransactionHexViewer extends HexViewer<RawTransactionPart> {
  public TransactionHexViewer() {
    super(new SimpleTransactionContextWidget());
  }

  public void setTransaction(final RawTransactionContainer rawTransaction) {
    clear();

    for (final RawTransactionPart part : rawTransaction) {
      addFields(part);
    }
  }

  @Override
  protected byte[] getBytesForValue(final RawTransactionPart value) {
    return value.getBytes();
  }

  @Override
  protected Color getFieldColor(final RawTransactionPart value) {
    return TransactionPartColorPicker.getFieldColor(value.getType());
  }
}
