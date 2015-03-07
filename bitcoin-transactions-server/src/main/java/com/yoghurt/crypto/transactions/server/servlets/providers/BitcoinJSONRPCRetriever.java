package com.yoghurt.crypto.transactions.server.servlets.providers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.DecoderException;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
import org.apache.http.util.EntityUtils;

import com.yoghurt.crypto.transactions.server.util.HttpClientProxy;
import com.yoghurt.crypto.transactions.server.util.json.JSONRPCEncoder;
import com.yoghurt.crypto.transactions.server.util.json.JSONRPCParser;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.config.BitcoinCoreNodeConfig;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;

public class BitcoinJSONRPCRetriever implements BlockchainRetrievalHook {
  private static final String JSON_RPC_REALM = "jsonrpc";
  private static final String AUTH_SCHEME = AuthSchemes.BASIC;

  private static final String ERROR_TX_FORMAT = "Could not retrieve transaction information: %s";

  private static final String URI_FORMAT = "http://%s:%s";

  private static final String GENESIS_COINBASE_TXID = "4A5E1E4BAAB89F3A32518A88C31BC87F618F76673E2CC77AB2127B7AFDEDA33B";
  private static final String GENESIS_COINBASE_RAW = "01000000010000000000000000000000000000000000000000000000000000000000000000FFFFFFFF4D04FFFF001D0104455468652054696D65732030332F4A616E2F32303039204368616E63656C6C6F72206F6E206272696E6B206F66207365636F6E64206261696C6F757420666F722062616E6B73FFFFFFFF0100F2052A01000000434104678AFDB0FE5548271967F1A67130B7105CD6A828E03909A67962E0EA1F61DEB649F6BC3F4CEF38C4F35504E51EC112DE5C384DF7BA0B8D578A4C702B6BF11D5FAC00000000";

  private final String uri;

  private final HttpClientContext localContext;
  private final CredentialsProvider credentialsProvider = new SystemDefaultCredentialsProvider();

  public BitcoinJSONRPCRetriever(final BitcoinCoreNodeConfig config) {
    this(config.getHost(), Integer.parseInt(config.getPort()), config.getRpcUser(), config.getRpcPass());
  }

  private BitcoinJSONRPCRetriever(final String host, final int port, final String rpcUser, final String rpcPassword) {
    uri = String.format(URI_FORMAT, host, port);
    credentialsProvider.setCredentials(new AuthScope(host, port, JSON_RPC_REALM, AUTH_SCHEME), new UsernamePasswordCredentials(rpcUser, rpcPassword));

    final AuthCache authCache = new BasicAuthCache();
    authCache.put(new HttpHost(host, port), new BasicScheme());

    localContext = HttpClientContext.create();
    localContext.setAuthCache(authCache);
  }

  @Override
  public String getRawTransactionHex(final String txid) throws ApplicationException {
    // Work-around for special case: genesis block coinbase
    // If RPC isn't gonna return this, I'm gonna fucking do it for it.
    if(GENESIS_COINBASE_TXID.equalsIgnoreCase(txid)) {
      return GENESIS_COINBASE_RAW;
    }

    try {
      return doSimpleJSONRPCMethod("getrawtransaction", txid);
    } catch (IOException | HttpException e) {
      throw new ApplicationException(String.format(ERROR_TX_FORMAT, txid));
    }
  }

  @Override
  public String getLastBlockHash() throws ApplicationException {
    try {
      return doSimpleJSONRPCMethod("getbestblockhash");
    } catch (IOException | HttpException e) {
      e.printStackTrace();
      throw new ApplicationException(e.getMessage());
    }
  }

  @Override
  public TransactionInformation getTransactionInformation(final String txid) throws ApplicationException {
    if(GENESIS_COINBASE_TXID.equalsIgnoreCase(txid)) {
      return null;
    }

    try (CloseableHttpClient client = getAuthenticatedHttpClientProxy();
        InputStream jsonData = doComplexJSONRPCMethod(client, "getrawtransaction", txid, 1)) {

      return JSONRPCParser.getTransactionInformation(jsonData);
    } catch (IOException | HttpException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public BlockInformation getBlockInformationFromHeight(final int height) throws ApplicationException {
    return getBlockInformationFromHash(getBlockHashFromHeight(height));
  }

  private String getBlockHashFromHeight(final int height) throws ApplicationException {
    try {
      return doSimpleJSONRPCMethod("getblockhash", height);
    } catch (final IOException | HttpException e) {
      e.printStackTrace();
      throw new ApplicationException(e.getMessage());
    }
  }

  @Override
  public BlockInformation getBlockInformationLast() throws ApplicationException {
    return getBlockInformationFromHash(getLastBlockHash());
  }

  @Override
  public BlockInformation getBlockInformationFromHash(final String identifier) throws ApplicationException {
    try (CloseableHttpClient client = getAuthenticatedHttpClientProxy();
        InputStream jsonData = doComplexJSONRPCMethod(client, "getblock", identifier)) {

      final BlockInformation blockInformation = JSONRPCParser.getBlockInformation(jsonData);

      // FIXME/TODO Refactor the below so we don't need to do 3 RPC calls, 2 of which are duplicate.
      // Also refactor in a way that BlockInformation's rawCoinbaseTransaction is not abused by setting
      // the coinbase txid in it, instead of the raw hex which it is meant for

      // Extract the coinbase tx id
      final String coinbaseTxid = blockInformation.getRawCoinbaseTransaction();

      // Retrieve raw coinbase tx and its blockchain information
      final String rawTransactionHex = getRawTransactionHex(coinbaseTxid);
      final TransactionInformation ti = getTransactionInformation(coinbaseTxid);

      // Stick it in the block info
      blockInformation.setCoinbaseInformation(ti);
      blockInformation.setRawCoinbaseTransaction(rawTransactionHex);

      // Return the result
      return blockInformation;
    } catch (IOException | HttpException | DecoderException e) {
      e.printStackTrace();
      throw new ApplicationException(e.getMessage());
    }
  }

  private String doSimpleJSONRPCMethod(final String method, final Object... params) throws IOException, HttpException {
    try (CloseableHttpClient client = getAuthenticatedHttpClientProxy(); InputStream stream = doComplexJSONRPCMethod(client, method, params)) {
      return JSONRPCParser.getResultString(stream);
    }
  }

  private InputStream doComplexJSONRPCMethod(final CloseableHttpClient client, final String method, final Object... params) throws IOException,
  IllegalStateException, ParseException, HttpException {
    final String payload = JSONRPCEncoder.getRequestString(method, params);

    // Temporary
    System.out.println("> " + payload);
    System.out.println("< " + EntityUtils.toString(HttpClientProxy.postRemoteContent(client, uri, payload)));

    return HttpClientProxy.postRemoteContent(client, uri, payload).getContent();
  }

  private CloseableHttpClient getAuthenticatedHttpClientProxy() {
    return HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
  }
}
