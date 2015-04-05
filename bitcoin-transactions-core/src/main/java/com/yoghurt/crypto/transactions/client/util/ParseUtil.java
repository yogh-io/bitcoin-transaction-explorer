package com.yoghurt.crypto.transactions.client.util;

import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.util.block.BlockParseUtil;
import com.yoghurt.crypto.transactions.client.util.transaction.TransactionParseUtil;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;

public final class ParseUtil {
  private ParseUtil() {}

  public static Block getBlockFromHex(final String hex) {
    return getBlockFromHex(new Block(), hex);
  }

  public static Transaction getTransactionFromHex(final String hex) {
    return getTransactionFromHex(new Transaction(), hex);
  }

  public static Block getBlockFromHex(final Block b, final String hex) {
    if (hex == null) {
      return null;
    }

    BlockParseUtil.parseBlockBytes(Hex.decode(hex), b);
    return b;
  }

  public static Transaction getTransactionFromHex(final Transaction t, final String hex) {
    if (hex == null) {
      return null;
    }

    TransactionParseUtil.parseTransactionBytes(Hex.decode(hex), t);
    return t;
  }
}
