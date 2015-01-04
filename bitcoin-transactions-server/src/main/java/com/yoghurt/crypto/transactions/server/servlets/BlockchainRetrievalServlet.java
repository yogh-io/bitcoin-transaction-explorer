package com.yoghurt.crypto.transactions.server.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.yoghurt.crypto.transactions.server.servlets.providers.LatestBlockRetriever;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalService;

@WebServlet("/application/blockchain-retrieve")
public class BlockchainRetrievalServlet extends RemoteServiceServlet implements BlockchainRetrievalService {
  private static final long serialVersionUID = 7984638304207123693L;

  private static BlockchainRetrievalFactory retriever;

  private LatestBlockRetriever latestBlockRetriever;

  @Override
  public void init(final ServletConfig config) throws ServletException {
    super.init(config);

    retriever = new BlockchainRetrievalFactory();

    latestBlockRetriever = new LatestBlockRetriever(retriever.get());
    latestBlockRetriever.start();
  }

  @Override
  public String getRawTransactionHex(final String txid) throws ApplicationException {
    return retriever.get().getRawTransactionHex(txid);
  }

  @Override
  public TransactionInformation getTransactionInformation(final String txid) throws ApplicationException {
    return retriever.get().getTransactionInformation(txid);
  }

  @Override
  public String getRawBlockHex(final int height) throws ApplicationException {
    return retriever.get().getRawBlockFromHeight(height);
  }

  @Override
  public String getRawBlockHex(final String blockHash) throws ApplicationException {
    return retriever.get().getRawBlockFromHash(blockHash);
  }

  @Override
  public String getLastRawBlockHex() throws ApplicationException {
    return retriever.get().getLastRawBlock();
  }


  @Override
  public BlockInformation getBlockInformation(final String identifier) throws ApplicationException {
    return retriever.get().getBlockInformation(identifier);
  }

  @Override
  public String getLatestBlockHash() {
    return latestBlockRetriever.getLatestHash();
  }

  @Override
  public void destroy() {
    latestBlockRetriever.stop();
  }
}
