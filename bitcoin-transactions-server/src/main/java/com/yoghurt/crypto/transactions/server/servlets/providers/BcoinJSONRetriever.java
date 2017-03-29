package com.yoghurt.crypto.transactions.server.servlets.providers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpException;
import org.apache.http.ParseException;
import org.apache.http.impl.client.CloseableHttpClient;

import com.yoghurt.crypto.transactions.server.domain.BcoinNodeConfig;
import com.yoghurt.crypto.transactions.server.util.HttpClientProxy;
import com.yoghurt.crypto.transactions.server.util.json.bcoin.BcoinJSONParser;
import com.yoghurt.crypto.transactions.server.util.json.core.JSONRPCParser;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.domain.ApplicationException;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.OutpointInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;

public class BcoinJSONRetriever extends BitcoinJSONRPCRetriever {
  public BcoinJSONRetriever(final BcoinNodeConfig config) {
    super(config.getHost(), Integer.parseInt(config.getPort()), "x", config.getRpcKey());
  }

  @Override
  public BlockInformation getBlockInformationFromHash(String blockHash) throws ApplicationException {
    return getBlockInformation(blockHash);
  }

  @Override
  public BlockInformation getBlockInformationFromHeight(int height) throws ApplicationException {
    return getBlockInformation(String.valueOf(height));
  }

  private BlockInformation getBlockInformation(String hashOrHeight) throws ApplicationException {
    try (CloseableHttpClient client = getHttpClientProxy(); InputStream inputStream = doGet(client, "/block/%s", hashOrHeight)) {
      BlockInformation blockInformation = BcoinJSONParser.getBlockInformation(inputStream);
      
      // Extract the coinbase tx id (TODO: Refactor, shouldn't be using a mock
      // object like this)
      final String coinbaseTxid = blockInformation.getCoinbaseInformation().getRawHex();

      // Retrieve raw coinbase tx and its blockchain information
      final TransactionInformation ti = getTransactionInformation(coinbaseTxid);

      // Stick it in the block info
      blockInformation.setCoinbaseInformation(ti);

      return blockInformation;
    } catch (IllegalStateException | ParseException | IOException | HttpException | URISyntaxException | DecoderException e) {
      throw new ApplicationException();
    }
  }

  @Override
  public BlockInformation getBlockInformationLast() throws ApplicationException {
    return getBlockInformationFromHash(getLatestBlockHash());
  }

  @Override
  public String getLatestBlockHash() throws ApplicationException {
    System.out.println("About to get chaintip..");
    
    try (CloseableHttpClient client = getHttpClientProxy(); InputStream inputStream = doGet(client, "/")) {
      System.out.println("Getting chaintip.");
      String parseChainTip = BcoinJSONParser.parseChainTip(inputStream);

      return parseChainTip;
    } catch (IllegalStateException | ParseException | IOException | HttpException | URISyntaxException e) {
      throw new ApplicationException();
    }
  }

  @Override
  public AddressInformation getAddressInformation(final String address) throws ApplicationException {
    try (final CloseableHttpClient client = getHttpClientProxy(); final InputStream jsonData = doGet(client, "/tx/address/%s", address)) {
      final AddressInformation addressInformation = BcoinJSONParser.getAddressInformation(address, jsonData);

      for (final OutpointInformation outpoint : addressInformation.getOutpoints()) {
        final String txid = outpoint.getReferenceTransaction();
        try (final InputStream utxoJsonData = doComplexJSONRPCMethod(client, "gettxout", txid, outpoint.getIndex()).getContent()) {
          outpoint.setSpent(JSONRPCParser.isNullResult(utxoJsonData));
        }
      }

      return addressInformation;
    } catch (IOException | HttpException | DecoderException | IllegalStateException | ParseException | URISyntaxException e) {
      e.printStackTrace();
      throw new ApplicationException(e.getMessage());
    }
  }

  private InputStream doGet(CloseableHttpClient client, final String method, Object... params)
      throws IOException, HttpException, IllegalStateException, ParseException, URISyntaxException {
    return doGet(client, String.format(method, params));
  }

  private InputStream doGet(CloseableHttpClient client, final String method)
      throws IOException, HttpException, IllegalStateException, ParseException, URISyntaxException {
    String requestUrl = String.format("%s%s", uri, method);

    System.out.println("> " + requestUrl);

    return HttpClientProxy.getRemoteContent(client, requestUrl).getContent();
  }
}
