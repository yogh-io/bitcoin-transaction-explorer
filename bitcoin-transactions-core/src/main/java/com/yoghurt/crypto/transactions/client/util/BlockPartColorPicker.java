package com.yoghurt.crypto.transactions.client.util;

import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.client.util.misc.ColorBuilder;
import com.yoghurt.crypto.transactions.shared.domain.BlockPartType;

public class BlockPartColorPicker {
  public static Color getFieldColor(final BlockPartType type) {
    Color color;

    switch (type) {
    case PREV_BLOCK_HASH:
      color = ColorBuilder.interpret("green");
      break;
    case MERKLE_ROOT:
      color = ColorBuilder.interpret("cornflowerblue");
      break;
    case TIMESTAMP:
      color = ColorBuilder.interpret("red");
      break;
    case BITS:
      color = ColorBuilder.interpret("cyan");
      break;
    case NONCE:
      color = ColorBuilder.interpret("lightgreen");
      break;
    case VERSION:
    default:
      color = ColorBuilder.interpret("grey");
      break;
    }

    return color;
  }
}
