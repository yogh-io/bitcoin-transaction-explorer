package com.yoghurt.crypto.transactions.server.servlets;

import javax.servlet.annotation.WebServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalService;

@WebServlet("/application/blockchain-retrieve")
public class BlockchainRetrievalServlet extends RemoteServiceServlet implements BlockchainRetrievalService {
  private static final long serialVersionUID = 7984638304207123693L;

  @Override
  public String getRawTransactionHex(final String txid) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getRawTransactionHex(txid);
  }

  @Override
  public TransactionInformation getTransactionInformation(final String txid) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getTransactionInformation(txid);
  }

  @Override
  public BlockInformation getBlockInformationFromHash(final String identifier) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getBlockInformationFromHash(identifier);
  }

  @Override
  public BlockInformation getBlockInformationFromHeight(final int height) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getBlockInformationFromHeight(height);
  }

  @Override
  public BlockInformation getBlockInformationLast() throws ApplicationException {
    return BlockchainRetrievalFactory.get().getBlockInformationLast();
  }

  @Override
  public String getLatestBlockHash() throws ApplicationException {
    return BlockchainRetrievalFactory.get().getLastBlockHash();
  }
}
