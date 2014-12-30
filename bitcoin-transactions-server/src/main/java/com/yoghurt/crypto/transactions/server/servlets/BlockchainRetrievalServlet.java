package com.yoghurt.crypto.transactions.server.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.yoghurt.crypto.transactions.server.servlets.providers.BlockchainRetrievalHook;
import com.yoghurt.crypto.transactions.server.servlets.providers.BlockrAPIRetriever;
import com.yoghurt.crypto.transactions.server.servlets.providers.LatestBlockRetriever;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalService;

@WebServlet("/application/blockchain-retrieve")
public class BlockchainRetrievalServlet extends RemoteServiceServlet implements BlockchainRetrievalService {
  private static final long serialVersionUID = 7984638304207123693L;

  private static class Retriever {
    public static BlockchainRetrievalHook create() {
      return new BlockrAPIRetriever();
    }
  }

  private LatestBlockRetriever latestBlockRetriever;

  @Override
  public void init(final ServletConfig config) throws ServletException {
    super.init(config);

    latestBlockRetriever = new LatestBlockRetriever(Retriever.create());
    latestBlockRetriever.start();
  }

  @Override
  public String getRawTransactionHex(final String txid) throws ApplicationException {
    return Retriever.create().getRawTransactionHex(txid);
  }

  @Override
  public TransactionInformation getTransactionInformation(final String txid) throws ApplicationException {
    return Retriever.create().getTransactionInformation(txid);
  }

  @Override
  public String getRawBlockHex(final int height) throws ApplicationException {
    return Retriever.create().getRawBlockFromHeight(height);
  }

  @Override
  public String getRawBlockHex(final String blockHash) throws ApplicationException {
    return Retriever.create().getRawBlockFromHash(blockHash);
  }

  @Override
  public String getLastRawBlockHex() throws ApplicationException {
    return Retriever.create().getLastRawBlock();
  }


  @Override
  public BlockInformation getBlockInformation(final String identifier) throws ApplicationException {
    return Retriever.create().getBlockInformation(identifier);
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
