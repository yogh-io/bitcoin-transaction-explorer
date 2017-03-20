package com.yoghurt.crypto.transactions.client.util;

import com.yoghurt.crypto.transactions.client.domain.BlockPartType;
import com.yoghurt.crypto.transactions.client.resources.R;
import com.yoghurt.crypto.transactions.client.util.misc.Color;

public class BlockPartColorPicker {
  public static Color getFieldColor(final BlockPartType type) {
    Color color;

    switch (type) {
    case PREV_BLOCK_HASH:
      color = R.color().blockHash();
      break;
    case MERKLE_ROOT:
      color = R.color().blockMerkleRoot();
      break;
    case TIMESTAMP:
      color = R.color().blockTime();
      break;
    case BITS:
      color = R.color().blockBits();
      break;
    case NONCE:
      color = R.color().blockNonce();
      break;
    case VERSION:
    default:
      color = R.color().blockVersion();
      break;
    }

    return color;
  }
}
