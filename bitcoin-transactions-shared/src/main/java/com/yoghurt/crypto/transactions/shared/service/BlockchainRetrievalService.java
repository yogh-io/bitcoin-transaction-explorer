package com.yoghurt.crypto.transactions.shared.service;


import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.yoghurt.crypto.transactions.shared.domain.JSONRPCMethod;
import com.yoghurt.crypto.transactions.shared.service.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.service.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.service.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.service.domain.exception.ApplicationException;

@RemoteServiceRelativePath("blockchain-retrieve")
public interface BlockchainRetrievalService extends RemoteService {
  TransactionInformation getTransactionInformation(String txid) throws ApplicationException;

  BlockInformation getBlockInformationFromHash(String blockHash) throws ApplicationException;

  BlockInformation getBlockInformationFromHeight(int height) throws ApplicationException;

  BlockInformation getBlockInformationLast() throws ApplicationException;

  String getLatestBlockHash() throws ApplicationException;

  String getJSONRPCResponse(JSONRPCMethod method, String[] arguments) throws ApplicationException;

  AddressInformation getAddressInformation(String address) throws ApplicationException ;

  ArrayList<String> getTransactionList(int height) throws ApplicationException;
}
