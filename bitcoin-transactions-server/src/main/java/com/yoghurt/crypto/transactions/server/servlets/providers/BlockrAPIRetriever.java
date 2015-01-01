package com.yoghurt.crypto.transactions.server.servlets.providers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;

import org.apache.commons.codec.DecoderException;
import org.apache.http.HttpException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.yoghurt.crypto.transactions.server.util.HttpClientProxy;
import com.yoghurt.crypto.transactions.server.util.json.BlockrApiParser;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;

public class BlockrAPIRetriever implements BlockchainRetrievalHook {
  private static final String BLOCK_EXPLORER_API_LATEST_BLOCK = "http://blockexplorer.com/q/latesthash";

  private static final String LAST_BLOCK_KEYWORD = "last";

  private static final String ERROR_TX_FORMAT = "Could not retrieve transaction information: %s";

  private static final String BLOCKR_API_TX_RAW_FORMAT = "http://btc.blockr.io/api/v1/tx/raw/%s";
  private static final String BLOCKR_API_TX_INFO_FORMAT = "http://btc.blockr.io/api/v1/tx/info/%s";

  private static final String BLOCKR_API_BLOCK_RAW_FORMAT = "http://btc.blockr.io/api/v1/block/raw/%s";
  private static final String BLOCKR_API_BLOCK_INFO_FORMAT = "http://btc.blockr.io/api/v1/block/info/%s";

  @Override
  public String getLatestBlockHash() {
    try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
      return EntityUtils.toString(HttpClientProxy.getRemoteContent(client, BLOCK_EXPLORER_API_LATEST_BLOCK));
    } catch (URISyntaxException | IOException | HttpException e) {
      e.printStackTrace();
    }

    return null;
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
  public String getLastRawBlock() {
    return getRawBlock(LAST_BLOCK_KEYWORD);
  }

  @Override
  public String getRawBlockFromHash(final String hash) {
    return getRawBlock(String.valueOf(hash));
  }

  @Override
  public String getRawBlockFromHeight(final int height) {
    return getRawBlock(String.valueOf(height));
  }

  private String getRawBlock(final String identifier) {
    try (CloseableHttpClient client = HttpClientProxy.buildProxyClient()) {
      final InputStream jsonData = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_BLOCK_RAW_FORMAT, identifier)).getContent();

      return BlockrApiParser.getRawBlockHex(jsonData);
    } catch (ParseException | URISyntaxException | IOException | HttpException | DecoderException e) {
      e.printStackTrace();
      return null;
    }
  }
}
