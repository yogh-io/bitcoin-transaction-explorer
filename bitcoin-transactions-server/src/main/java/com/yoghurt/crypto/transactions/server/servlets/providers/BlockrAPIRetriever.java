package com.yoghurt.crypto.transactions.server.servlets.providers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;

import org.apache.commons.codec.DecoderException;
import org.apache.http.HttpException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.yoghurt.crypto.transactions.server.util.HttpClientProxy;
import com.yoghurt.crypto.transactions.server.util.json.BlockrApiParser;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.JSONRPCMethod;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException.Reason;

public class BlockrAPIRetriever implements BlockchainRetrievalHook {
  private static final String BLOCKR_API_LATEST_BLOCK = "http://btc.blockr.io/api/v1/block/info/last";

  private static final String LAST_BLOCK_KEYWORD = "last";

  private static final String ERROR_TX_FORMAT = "Could not retrieve transaction information: %s";

  private static final String BLOCKR_API_TX_RAW_FORMAT = "http://btc.blockr.io/api/v1/tx/raw/%s";
  private static final String BLOCKR_API_TX_INFO_FORMAT = "http://btc.blockr.io/api/v1/tx/info/%s";

  private static final String BLOCKR_API_BLOCK_RAW_FORMAT = "http://btc.blockr.io/api/v1/block/raw/%s";
  private static final String BLOCKR_API_BLOCK_INFO_FORMAT = "http://btc.blockr.io/api/v1/block/info/%s";

  @Override
  public String getLastBlockHash() {
    try (CloseableHttpClient client = HttpClientBuilder.create().build();
        InputStream jsonData = HttpClientProxy.getRemoteContent(client, BLOCKR_API_LATEST_BLOCK).getContent()) {

      return BlockrApiParser.getBlockHash(jsonData);
    } catch (URISyntaxException | IOException | HttpException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public String getRawTransactionHex(final String txid) throws ApplicationException {
    try (final CloseableHttpClient client = HttpClientProxy.buildProxyClient();
        InputStream stream = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_TX_RAW_FORMAT, txid)).getContent()) {
      return BlockrApiParser.getRawTransactionHex(stream);
    } catch (URISyntaxException | IOException | HttpException e) {
      e.printStackTrace();
      throw new ApplicationException(String.format(ERROR_TX_FORMAT, txid));
    }
  }

  @Override
  public TransactionInformation getTransactionInformation(final String txid) throws ApplicationException {
    try (final CloseableHttpClient client = HttpClientProxy.buildProxyClient();
        final InputStream jsonDataInfo = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_TX_INFO_FORMAT, txid)).getContent();
        final InputStream jsonDataRaw = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_TX_RAW_FORMAT, txid)).getContent()) {

      final TransactionInformation transactionInformation = BlockrApiParser.getTransactionInformation(jsonDataInfo);

      // We need to do another call just for the block hash
      final String hash = BlockrApiParser.getBlockHashFromTransaction(jsonDataRaw);
      transactionInformation.setBlockHash(hash);

      return transactionInformation;
    } catch (ParseException | URISyntaxException | IOException | HttpException e) {
      e.printStackTrace();
      return null;
    }
  }

  public BlockInformation getBlockInformation(final String identifier) throws ApplicationException {
    try (CloseableHttpClient client = HttpClientProxy.buildProxyClient();
        InputStream jsonDataInfo = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_BLOCK_INFO_FORMAT, identifier)).getContent();
        InputStream jsonDataRaw = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_BLOCK_RAW_FORMAT, identifier)).getContent()) {

      final BlockInformation blockInformation = BlockrApiParser.getBlockInformation(jsonDataInfo);
      blockInformation.setRawBlockHeaders(getRawBlock(identifier));

      final String coinbaseTransactionHash = BlockrApiParser.getCoinbaseTransaction(jsonDataRaw);

      final String rawCoinbase = getRawTransactionHex(coinbaseTransactionHash);
      final TransactionInformation coinbaseInformation = getTransactionInformation(coinbaseTransactionHash);
      blockInformation.setRawCoinbaseTransaction(rawCoinbase);
      blockInformation.setCoinbaseInformation(coinbaseInformation);

      return blockInformation;
    } catch (ParseException | URISyntaxException | IOException | HttpException | DecoderException e) {
      e.printStackTrace();
      return null;
    }
  }

  public String getLastRawBlock() {
    return getRawBlock(LAST_BLOCK_KEYWORD);
  }

  public String getRawBlockFromHash(final String hash) {
    return getRawBlock(String.valueOf(hash));
  }

  public String getRawBlockFromHeight(final int height) {
    return getRawBlock(String.valueOf(height));
  }

  private String getRawBlock(final String identifier) {
    try (CloseableHttpClient client = HttpClientProxy.buildProxyClient();
        InputStream jsonData = HttpClientProxy.getRemoteContent(client, String.format(BLOCKR_API_BLOCK_RAW_FORMAT, identifier)).getContent()) {

      return BlockrApiParser.getRawBlockHex(jsonData);
    } catch (ParseException | URISyntaxException | IOException | HttpException | DecoderException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public BlockInformation getBlockInformationFromHash(final String identifier) throws ApplicationException {
    return getBlockInformation(identifier);
  }

  @Override
  public BlockInformation getBlockInformationFromHeight(final int height) throws ApplicationException {
    return getBlockInformation(String.valueOf(height));
  }

  @Override
  public BlockInformation getBlockInformationLast() throws ApplicationException {
    return getBlockInformationFromHash(LAST_BLOCK_KEYWORD);
  }

  @Override
  public String getJSONRPCResponse(final JSONRPCMethod method, final String[] arguments) throws ApplicationException {
    throw new ApplicationException(Reason.UNSUPPORTED_OPERATION);
  }
}
