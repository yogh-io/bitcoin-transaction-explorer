package com.yoghurt.crypto.transactions.client.widget;

import com.yoghurt.crypto.transactions.client.domain.transaction.RawTransactionContainer;
import com.yoghurt.crypto.transactions.client.domain.transaction.RawTransactionPart;
import com.yoghurt.crypto.transactions.client.domain.transaction.Transaction;
import com.yoghurt.crypto.transactions.client.domain.transaction.TransactionPartType;
import com.yoghurt.crypto.transactions.client.util.TransactionEncodeUtil;

public class TransactionHexViewer extends HexViewer<TransactionPartType> {
  public void setTransaction(final Transaction transaction) {
    final RawTransactionContainer rawTransaction = TransactionEncodeUtil.encodeTransaction(transaction);

    for (final RawTransactionPart part : rawTransaction) {
      final TransactionPartType type = part.getType();

      for (final byte bite : part.getBytes()) {
        addField(bite, type);
      }
    }
  }

  @Override
  protected String getColorForType(final TransactionPartType type) {
    final String color;

    switch (type) {
    case OUTPUT_SIZE:
    case OUTPUT_SCRIPT_LENGTH:
    case INPUT_SCRIPT_LENGTH:
    case INPUT_SIZE:
      color = "pink";
      break;
    case INPUT_OUTPOINT_HASH:
      color= "brown";
      break;
    case INPUT_OUTPOINT_INDEX:
      color = "black";
      break;
    case INPUT_SEQUENCE:
      color = "lightGreen";
      break;
    case INPUT_SIGNATURE_SCRIPT:
      color = "red";
      break;
    case OUTPUT_VALUE:
      color = "blue";
      break;
    case OUTPUT_SIGNATURE_SCRIPT:
      color = "cyan";
      break;
    case LOCK_TIME:
    case VERSION:
    default:
      color = "grey";
      break;
    }

    return color;
  }
}
