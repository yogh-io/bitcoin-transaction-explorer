package com.yoghurt.crypto.transactions.shared.service;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;

@RemoteServiceRelativePath("blockchain-retrieve")
public interface BlockchainRetrievalService extends RemoteService {
  String getRawTransactionHex(String txid) throws ApplicationException;

  TransactionInformation getTransactionInformation(String txid) throws ApplicationException;
}
