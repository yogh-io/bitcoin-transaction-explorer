package com.yoghurt.crypto.transactions.server.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.codec.DecoderException;
import org.apache.http.HttpException;
import org.apache.http.impl.client.CloseableHttpClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.yoghurt.crypto.transactions.server.servlets.background.LatestBlockRetriever;
import com.yoghurt.crypto.transactions.server.servlets.util.HttpClientProxy;
import com.yoghurt.crypto.transactions.server.servlets.util.json.BlockrApiParser;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalService;

@WebServlet("/application/blockchain-retrieve")
public class BlockchainRetrievalServlet extends RemoteServiceServlet implements BlockchainRetrievalService {
  private static final String LAST_BLOCK_KEYWORD = "last";

  private static final long serialVersionUID = 7984638304207123693L;

  private static final String ERROR_TX_FORMAT = "Could not retrieve transaction information: %s";

  private static final String BLOCKR_API_TX_RAW_FORMAT = "http://btc.blockr.io/api/v1/tx/raw/%s";
  private static final String BLOCKR_API_TX_INFO_FORMAT = "http://btc.blockr.io/api/v1/tx/info/%s";

  private static final String BLOCKR_API_BLOCK_RAW_FORMAT = "http://btc.blockr.io/api/v1/block/raw/%s";
  private static final String BLOCKR_API_BLOCK_INFO_FORMAT = "http://btc.blockr.io/api/v1/block/info/%s";

  private LatestBlockRetriever blockRetriever;

  @Override
  public void init(final ServletConfig config) throws ServletException {
    super.init(config);

    blockRetriever = new LatestBlockRetriever();
    blockRetriever.start();
  }

  @Override
  public String getRawTransactionHex(final String txid) throws ApplicationException {
    try (CloseableHttpClient client = HttpClientProxy.buildProxyClient()) {
      final InputStream jsonData = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_TX_RAW_FORMAT, txid)).getContent();

      return BlockrApiParser.getRawTransactionHex(jsonData);
    } catch (URISyntaxException | IOException | HttpException e) {
      e.printStackTrace();
      throw new ApplicationException(String.format(ERROR_TX_FORMAT, txid));
    }
  }

  @Override
  public TransactionInformation getTransactionInformation(final String txid) throws ApplicationException {
    try (CloseableHttpClient client = HttpClientProxy.buildProxyClient()) {
      final InputStream jsonData = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_TX_INFO_FORMAT, txid)).getContent();

      final TransactionInformation transactionInformation = BlockrApiParser.getTransactionInformation(jsonData);

      return transactionInformation;
    } catch (ParseException | URISyntaxException | IOException | HttpException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String getRawBlockHex(final int height) {
    return getRawBlockHexUniversal(String.valueOf(height));
  }

  @Override
  public String getRawBlockHex(final String blockHash) {
    return getRawBlockHexUniversal(blockHash);
  }

  @Override
  public String getLastRawBlockHex() {
    return getRawBlockHexUniversal(LAST_BLOCK_KEYWORD);
  }

  private static String getRawBlockHexUniversal(final String identifier) {
    try (CloseableHttpClient client = HttpClientProxy.buildProxyClient()) {
      final InputStream jsonData = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_BLOCK_RAW_FORMAT, identifier)).getContent();

      return BlockrApiParser.getRawBlockHex(jsonData);
    } catch (ParseException | URISyntaxException | IOException | HttpException | DecoderException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public BlockInformation getBlockInformation(final String identifier) {
    try (CloseableHttpClient client = HttpClientProxy.buildProxyClient()) {
      final InputStream jsonData = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_BLOCK_INFO_FORMAT, identifier)).getContent();

      return BlockrApiParser.getBlockInformation(jsonData);
    } catch (ParseException | URISyntaxException | IOException | HttpException | DecoderException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String getLatestBlockHash() {
    return blockRetriever.getLatestHash();
  }

  @Override
  public void destroy() {
    blockRetriever.stop();
  }
}
