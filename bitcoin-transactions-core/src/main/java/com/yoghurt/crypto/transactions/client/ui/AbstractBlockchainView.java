package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.yoghurt.crypto.transactions.client.domain.Block;
import com.yoghurt.crypto.transactions.client.domain.Transaction;
import com.yoghurt.crypto.transactions.client.util.ParseUtil;

public class AbstractBlockchainView extends Composite implements BlockchainView {
  @Override
  public Block getBlockFromHex(final String hex) {
    return ParseUtil.getBlockFromHex(hex);
  }

  @Override
  public Transaction getTransactionFromHex(final String hex) {
    return ParseUtil.getTransactionFromHex(new Transaction(), hex);
  }

  @Override
  public Block getBlockFromHex(final Block b, final String hex) {
    return ParseUtil.getBlockFromHex(b, hex);
  }

  @Override
  public Transaction getTransactionFromHex(final Transaction t, final String hex) {
    return ParseUtil.getTransactionFromHex(t, hex);
  }
}
