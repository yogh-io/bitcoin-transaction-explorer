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
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;

public class BitcoinJSONRPCRetriever implements BlockchainRetrievalHook {
  private static final String JSON_RPC_REALM = "jsonrpc";
  private static final String AUTH_SCHEME = AuthSchemes.BASIC;

  private static final String ERROR_TX_FORMAT = "Could not retrieve transaction information: %s";

  private static final String URI_FORMAT = "http://%s:%s";

  private final String uri;

  private final HttpClientContext localContext;
  private final CredentialsProvider credentialsProvider = new SystemDefaultCredentialsProvider();

  public BitcoinJSONRPCRetriever(final String host, final int port, final String rpcUser, final String rpcPassword) {
    uri = String.format(URI_FORMAT, host, port);
    credentialsProvider.setCredentials(new AuthScope(host, port, JSON_RPC_REALM, AUTH_SCHEME), new UsernamePasswordCredentials(rpcUser, rpcPassword));

    final AuthCache authCache = new BasicAuthCache();
    authCache.put(new HttpHost(host, port), new BasicScheme());

    localContext = HttpClientContext.create();
    localContext.setAuthCache(authCache);
  }

  @Override
  public String getRawTransactionHex(final String txid) throws ApplicationException {
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
  public String getLastRawBlock() throws ApplicationException {
    return getRawBlockFromHash(getLastBlockHash());
  }

  @Override
  public TransactionInformation getTransactionInformation(final String txid) throws ApplicationException {
    try (CloseableHttpClient client = getAuthenticatedHttpClientProxy();
        InputStream jsonData = doComplexJSONRPCMethod(client, "getrawtransaction", txid, 1)) {

      return JSONRPCParser.getTransactionInformation(jsonData);
    } catch (IOException | HttpException e) {
      e.printStackTrace();
      throw new ApplicationException(e.getMessage());
    }
  }

  @Override
  public String getRawBlockFromHash(final String identifier) throws ApplicationException {
    try (CloseableHttpClient client = getAuthenticatedHttpClientProxy();
        InputStream jsonData = doComplexJSONRPCMethod(client, "getblock", identifier)) {

      return JSONRPCParser.getRawBlock(jsonData);
    } catch (IOException | HttpException | DecoderException e) {
      e.printStackTrace();
      throw new ApplicationException(e.getMessage());
    }
  }

  @Override
  public String getRawBlockFromHeight(final int height) throws ApplicationException {
    return getRawBlockFromHash(getBlockHashFromHeight(height));
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
  public BlockInformation getBlockInformation(final String identifier) throws ApplicationException {
    try (CloseableHttpClient client = getAuthenticatedHttpClientProxy();
        InputStream jsonData = doComplexJSONRPCMethod(client, "getblock", identifier)) {

      return JSONRPCParser.getBlockInformation(jsonData);
    } catch (IOException | HttpException e) {
      e.printStackTrace();
      throw new ApplicationException(e.getMessage());
    }
  }

  private String doSimpleJSONRPCMethod(final String method, final Object... params) throws IOException, HttpException {
    try (CloseableHttpClient client = getAuthenticatedHttpClientProxy();
        InputStream stream = doComplexJSONRPCMethod(client, method, params)) {
      return JSONRPCParser.getResultString(stream);
    }
  }

  private InputStream doComplexJSONRPCMethod(final CloseableHttpClient client, final String method, final Object... params) throws IOException, IllegalStateException, ParseException, HttpException {
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
