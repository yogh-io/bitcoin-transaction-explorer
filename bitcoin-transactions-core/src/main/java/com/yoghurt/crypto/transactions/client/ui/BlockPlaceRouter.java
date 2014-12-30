package com.yoghurt.crypto.transactions.client.ui;


public interface BlockPlaceRouter {
  void goToBlock(int height);

  void goToBlock(String blockHash);
}
