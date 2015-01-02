package com.yoghurt.crypto.transactions.server.servlets.providers;

import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;

public interface BlockchainRetrievalHook {

  String getRawTransactionHex(String txid) throws ApplicationException;

  TransactionInformation getTransactionInformation(String txid) throws ApplicationException;

  String getRawBlockFromHash(String identifier) throws ApplicationException;

  String getRawBlockFromHeight(int height) throws ApplicationException;

  BlockInformation getBlockInformation(String identifier) throws ApplicationException;

  String getLastBlockHash() throws ApplicationException;

  String getLastRawBlock() throws ApplicationException;

}
