package com.yoghurt.crypto.transactions.client.place;


public interface BlockPlaceRouter {
  void goToBlock(int height);

  void goToBlock(String blockHash);
}
