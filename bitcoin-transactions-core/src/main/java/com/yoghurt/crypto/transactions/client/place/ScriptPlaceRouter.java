package com.yoghurt.crypto.transactions.client.place;

public interface ScriptPlaceRouter {

  void goToScript(String transactionHash, int outputIndex, String scriptSig);
}
