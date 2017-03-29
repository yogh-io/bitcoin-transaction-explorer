package com.yoghurt.crypto.transactions.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;

public interface BlockchainRetrievalServiceAsync {
  void getTransactionInformation(String txid, AsyncCallback<TransactionInformation> callback);

  void getBlockInformationFromHash(String blockHash, AsyncCallback<BlockInformation> callback);

  void getBlockInformationFromHeight(int height, AsyncCallback<BlockInformation> callback);

  void getBlockInformationLast(AsyncCallback<BlockInformation> callback);

  void getLatestBlockHash(AsyncCallback<String> callback);

  void getAddressInformation(String address, AsyncCallback<AddressInformation> callback);

  void getTransactionList(int height, AsyncCallback<ArrayList<String>> callback);
}
