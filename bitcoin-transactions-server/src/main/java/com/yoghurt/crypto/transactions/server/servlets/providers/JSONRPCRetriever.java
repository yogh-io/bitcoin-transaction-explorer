package com.yoghurt.crypto.transactions.server.servlets.providers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
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

import com.yoghurt.crypto.transactions.server.util.HttpClientProxy;
import com.yoghurt.crypto.transactions.server.util.json.JSONRPCEncoder;
import com.yoghurt.crypto.transactions.server.util.json.JSONRPCParser;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;

public class JSONRPCRetriever implements BlockchainRetrievalHook {
  private static final String JSON_RPC_REALM = "jsonrpc";
  private static final String AUTH_SCHEME = AuthSchemes.BASIC;

  private static final String ERROR_TX_FORMAT = "Could not retrieve transaction information: %s";

  private static final String URI_FORMAT = "http://%s:%s";

  private final String uri;

  private final HttpClientContext localContext;
  private final CredentialsProvider credentialsProvider = new SystemDefaultCredentialsProvider();

  public JSONRPCRetriever(final String host, final int port, final String rpcUser, final String rpcPassword) {
    uri = String.format(URI_FORMAT, host, port);
    credentialsProvider.setCredentials(new AuthScope(host, port, JSON_RPC_REALM, AUTH_SCHEME), new UsernamePasswordCredentials(rpcUser, rpcPassword));

    final AuthCache authCache = new BasicAuthCache();
    authCache.put(new HttpHost(host, port), new BasicScheme());

    localContext = HttpClientContext.create();
    localContext.setAuthCache(authCache);
  }

  @Override
  public String getLatestBlockHash() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getRawTransactionHex(final String txid) throws ApplicationException {
    System.out.println("Getting " + txid);
    try (CloseableHttpClient client = getAuthenticatedHttpClientProxy()) {
      final String payload = JSONRPCEncoder.getRequestString("getrawtransaction", txid);

      final InputStream is = HttpClientProxy.postRemoteContent(client, uri, payload).getContent();

      return JSONRPCParser.getRawTransactionHex(is);
    } catch (IOException | HttpException e) {
      e.printStackTrace();
      throw new ApplicationException(String.format(ERROR_TX_FORMAT, txid));
    }
  }

  @Override
  public TransactionInformation getTransactionInformation(final String txid) throws ApplicationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getRawBlockFromHash(final String identifier) throws ApplicationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getRawBlockFromHeight(final int height) throws ApplicationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BlockInformation getBlockInformation(final String identifier) throws ApplicationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getLastRawBlock() throws ApplicationException {
    // TODO Auto-generated method stub
    return null;
  }

  private CloseableHttpClient getAuthenticatedHttpClientProxy() {
    return HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
  }
}
