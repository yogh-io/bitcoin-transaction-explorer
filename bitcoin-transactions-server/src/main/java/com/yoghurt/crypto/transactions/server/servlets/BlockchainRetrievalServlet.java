package com.yoghurt.crypto.transactions.server.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.domain.ApplicationException;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrieveMethod;

@WebServlet("/application/blockchain-retrieve")
public class BlockchainRetrievalServlet extends HttpServlet {
  private static final long serialVersionUID = 7984638304207123693L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    BlockchainRetrieveMethod method = BlockchainRetrieveMethod.fromName(req.getParameter("method"));
    if (method == null) {
      resp.setStatus(400);
      return;
    }

    String parameter = req.getParameter("param");

    Object response = null;

    try {
      switch (method) {
      case GET_TRANSACTION_INFORMATION:
        response = getTransactionInformation(parameter);
        break;
      case GET_BLOCK_INFORMATION_FROM_HASH:
        response = getBlockInformationFromHash(parameter);
        break;
      case GET_BLOCK_INFORMATION_FROM_HEIGHT:
        response = getBlockInformationFromHeight(Integer.parseInt(parameter));
        break;
      case GET_BLOCK_INFORMATION_LAST:
        response = getBlockInformationLast();
        break;
      case GET_LATEST_BLOCK_HASH:
        response = getLatestBlockHash();
        break;
      case GET_ADDRESS_INFORMATION:
        response = getAddressInformation(parameter);
        break;
      case GET_TRANSACTION_LIST:
        response = getTransactionList(Integer.parseInt(parameter));
        break;
      }
    } catch (ApplicationException e) {
      e.printStackTrace();
      response = e;
    }

    if (response instanceof Throwable) {
      resp.setStatus(500);
    }

    // Object to JSON in file
    new ObjectMapper().writeValue(resp.getOutputStream(), response);
  }

  private TransactionInformation getTransactionInformation(final String txid) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getTransactionInformation(txid);
  }

  private BlockInformation getBlockInformationFromHash(final String identifier) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getBlockInformationFromHash(identifier);
  }

  private BlockInformation getBlockInformationFromHeight(final int height) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getBlockInformationFromHeight(height);
  }

  private BlockInformation getBlockInformationLast() throws ApplicationException {
    return BlockchainRetrievalFactory.get().getBlockInformationLast();
  }

  private String getLatestBlockHash() throws ApplicationException {
    return BlockchainRetrievalFactory.get().getLatestBlockHash();
  }

  private AddressInformation getAddressInformation(final String address) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getAddressInformation(address);
  }

  private ArrayList<String> getTransactionList(final int height) throws ApplicationException {
    return BlockchainRetrievalFactory.get().getTransactionList(height);
  }
}
