package com.yoghurt.crypto.transactions.client.widget.transaction;

import com.yoghurt.crypto.transactions.client.domain.transaction.RawTransactionContainer;
import com.yoghurt.crypto.transactions.client.domain.transaction.RawTransactionPart;
import com.yoghurt.crypto.transactions.client.util.Color;
import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.widget.HexViewer;

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
  protected byte[] getBytesForvalue(final RawTransactionPart value) {
    return value.getBytes();
  }

  @Override
  protected Color getFieldColor(final RawTransactionPart value) {
    return TransactionPartColorPicker.getFieldColor(value.getType());
  }
}
