package com.yoghurt.crypto.transactions.client.ui;

import com.yoghurt.crypto.transactions.client.domain.Block;
import com.yoghurt.crypto.transactions.client.domain.Transaction;

public interface BlockchainView {
  Block getBlockFromHex(final String hex);

  Transaction getTransactionFromHex(final String hex);

  Block getBlockFromHex(final Block b, final String hex);

  Transaction getTransactionFromHex(final Transaction t, final String hex);
}
