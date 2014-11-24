package com.yoghurt.crypto.transactions.server.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;

import javax.servlet.annotation.WebServlet;

import org.apache.http.HttpException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.yoghurt.crypto.transactions.server.servlets.util.HttpClientProxy;
import com.yoghurt.crypto.transactions.server.servlets.util.json.BlockrApiParser;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalService;

@WebServlet("/application/blockchain-retrieve")
public class BlockchainRetrievalServlet extends RemoteServiceServlet implements BlockchainRetrievalService {
  private static final long serialVersionUID = 7984638304207123693L;

  private static final String ERROR_TX_FORMAT = "Could not retrieve transaction information: %s";

  private final String BLOCKR_API_TX_RAW_FORMAT = "http://btc.blockr.io/api/v1/tx/raw/%s";
  private final String BLOCKR_API_TX_INFO_FORMAT = "http://btc.blockr.io/api/v1/tx/info/%s";

  @Override
  public String getRawTransactionHex(final String txid) throws ApplicationException {
    try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
      final InputStream jsonData = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_TX_RAW_FORMAT, txid));

      return BlockrApiParser.getRawTransactionHex(jsonData);
    } catch (URISyntaxException | IOException | HttpException e) {
      e.printStackTrace();
      throw new ApplicationException(String.format(ERROR_TX_FORMAT, txid));
    }
  }

  @Override
  public TransactionInformation getTransactionInformation(final String txid) throws ApplicationException {
    try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
      final InputStream jsonData = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_TX_INFO_FORMAT, txid));

      final TransactionInformation transactionInformation = BlockrApiParser.getTransactionInformation(jsonData);

      return transactionInformation;
    } catch (ParseException | URISyntaxException | IOException | HttpException e) {
      return null;
    }
  }
}
