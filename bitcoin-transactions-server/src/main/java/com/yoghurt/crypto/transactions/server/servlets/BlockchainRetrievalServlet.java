package com.yoghurt.crypto.transactions.server.servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.yoghurt.crypto.transactions.shared.domain.JSONRPCMethod;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalService;
import com.yoghurt.crypto.transactions.shared.service.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.service.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.service.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.service.domain.exception.ApplicationException;

@WebServlet("/application/blockchain-retrieve")
public class BlockchainRetrievalServlet extends RemoteServiceServlet implements BlockchainRetrievalService {
  private static final long serialVersionUID = 7984638304207123693L;

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
    return BlockchainRetrievalFactory.get().getLatestBlockHash();
  }

  @Override
  public String getJSONRPCResponse(final JSONRPCMethod method, final String[] arguments) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getJSONRPCResponse(method, arguments);
  }

  @Override
  public AddressInformation getAddressInformation(final String address) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getAddressInformation(address);
  }

  @Override
  public ArrayList<String> getTransactionList(final int height) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getTransactionList(height);
  }
}
