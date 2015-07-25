package com.yoghurt.crypto.transactions.server.servlets.providers;

import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.JSONRPCMethod;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;

public interface BlockchainRetrievalHook {

  String getRawTransactionHex(String txid) throws ApplicationException;

  TransactionInformation getTransactionInformation(String txid) throws ApplicationException;

  BlockInformation getBlockInformationFromHash(String identifier) throws ApplicationException;

  BlockInformation getBlockInformationFromHeight(int height) throws ApplicationException;

  BlockInformation getBlockInformationLast() throws ApplicationException;

  String getLastBlockHash() throws ApplicationException;

  String getJSONRPCResponse(JSONRPCMethod method, String[] arguments) throws ApplicationException;
}
