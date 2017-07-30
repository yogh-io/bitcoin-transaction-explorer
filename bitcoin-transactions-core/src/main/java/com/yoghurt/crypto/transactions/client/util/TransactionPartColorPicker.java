package com.yoghurt.crypto.transactions.client.util;

import com.yoghurt.crypto.transactions.client.resources.R;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;

/**
 * TODO Move to proper package.
 */
public class TransactionPartColorPicker {
  public static Color getFieldColor(final TransactionPartType type) {
    Color color;

    switch (type) {
    case INPUT_PREVIOUS_TRANSACTION_HASH:
      color = R.color().transactionHash();
      break;
    case INPUT_OUTPOINT_INDEX:
      color = R.color().transactionInputIndex();
      break;
    case OUTPUT_SCRIPT_LENGTH:
      color = R.color().transactionPubKeySigLength();
      break;
    case INPUT_SCRIPT_LENGTH:
      color = R.color().transactionScriptSigLength();
      break;
    case INPUT_SEQUENCE:
      color = R.color().transactionInputSequence();
      break;
    case INPUT_SIZE:
      color = R.color().transactionInputLength();
      break;
    case OUTPUT_SIZE:
      color = R.color().transactionOutputLength();
      break;
    case OUTPUT_VALUE:
      color = R.color().transactionOutputAmount();
      break;
    case SCRIPT_SIG_OP_CODE:
      color = R.color().transactionScriptSigOpCode();
      break;
    case SCRIPT_PUB_KEY_OP_CODE:
      color = R.color().transactionScriptSigOpCode();
      break;
    case SCRIPT_SIG_PUSH_DATA:
      color = R.color().transactionScriptSigPushData();
      break;
    case SCRIPT_SIG_PUSH_DATA_EXTRA:
      color = R.color().transactionPubKeySigPushDataExtra();
      break;
    case SCRIPT_PUB_KEY_PUSH_DATA:
      color = R.color().transactionPubKeySigPushData();
      break;
    case SCRIPT_PUB_KEY_PUSH_DATA_EXTRA:
      color = R.color().transactionPubKeySigPushDataExtra();
      break;
    case COINBASE_SCRIPT_SIG:
      color = R.color().transactionArbitraryData();
      break;
    case LOCK_TIME:
      color = R.color().transactionLockTime();
      break;
    case VERSION:
      color = R.color().transactionVersion();
      break;
    case WITNESS_MARKER:
      color = R.color().witnessMarker();
      break;
    case WITNESS_FLAG:
      color = R.color().witnessFlag();
      break;
    case WITNESS_ITEM_LENGTH:
      color = R.color().witnessItemLength();
      break;
    case WITNESS_PUSH_DATA_LENGTH:
      color = R.color().witnessPushDataLength();
      break;
    case WITNESS_PUSH_DATA:
      color = R.color().witnessPushData();
      break;
    default:
      color = R.color().transactionArbitraryData();
      break;
    }

    return color;
  }
}
