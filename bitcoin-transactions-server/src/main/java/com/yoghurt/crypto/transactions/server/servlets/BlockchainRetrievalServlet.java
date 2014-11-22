package com.yoghurt.crypto.transactions.server.servlets;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.annotation.WebServlet;

import org.apache.commons.codec.DecoderException;
import org.apache.http.HttpException;
import org.apache.http.ParseException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.yoghurt.crypto.transactions.server.servlets.util.HttpClientProxy;
import com.yoghurt.crypto.transactions.server.servlets.util.json.BlockrApiParser;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalService;

@WebServlet("/application/blockchain-retrieve")
public class BlockchainRetrievalServlet extends RemoteServiceServlet implements BlockchainRetrievalService {
  private static final long serialVersionUID = 7984638304207123693L;

  private final String BLOCKR_API_TX_FORMAT = "http://btc.blockr.io/api/v1/tx/raw/%s";

  @Override
  public String getRawTransactionHex(final String txid) throws Exception {
    System.out.println(txid);

    try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
      final byte[] jsonData = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_TX_FORMAT, txid));

      return BlockrApiParser.getRawTransactionHex(jsonData);
    } catch (ParseException | URISyntaxException | IOException | HttpException | DecoderException e) {
      e.printStackTrace();
      throw new Exception("Could not retrieve tx info.");
    }
  }
}
