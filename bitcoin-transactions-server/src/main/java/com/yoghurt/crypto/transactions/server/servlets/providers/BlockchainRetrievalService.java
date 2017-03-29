package com.yoghurt.crypto.transactions.server.servlets.providers;


import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.domain.ApplicationException;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;

public interface BlockchainRetrievalService extends RemoteService {
  TransactionInformation getTransactionInformation(String txid) throws ApplicationException;

  BlockInformation getBlockInformationFromHash(String blockHash) throws ApplicationException;

  BlockInformation getBlockInformationFromHeight(int height) throws ApplicationException;

  BlockInformation getBlockInformationLast() throws ApplicationException;

  String getLatestBlockHash() throws ApplicationException;

  AddressInformation getAddressInformation(String address) throws ApplicationException ;

  ArrayList<String> getTransactionList(int height) throws ApplicationException;
}
