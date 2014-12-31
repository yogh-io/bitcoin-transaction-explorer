package com.yoghurt.crypto.transactions.server.util;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Wrapper to get remote content and return it as an inputstream simply.
 */
public class HttpClientProxy {
  private static final int TIMEOUT = 5 * 1000;

  private final static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT).build();

  public static HttpEntity getRemoteContent(final HttpClient client, final String url) throws URISyntaxException, ClientProtocolException,
  IOException, ParseException, HttpException {
    return executeRemoteContent(client, new HttpGet(url));
  }

  public static HttpEntity postRemoteContent(final HttpClient client, final String url, final String payload) throws ClientProtocolException,
  IOException, ParseException, HttpException {
    final HttpPost httpPost = new HttpPost(url);
    httpPost.setEntity(new StringEntity(payload));

    return executeRemoteContent(client, httpPost);
  }

  public static HttpEntity postRemoteContent(final CloseableHttpClient client, final String uri, final String req,
      final HttpClientContext localContext) throws ClientProtocolException, ParseException, IOException, HttpException {
    final HttpPost httpPost = new HttpPost(uri);
    httpPost.setEntity(new StringEntity(req));

    return executeRemoteContent(client, httpPost, localContext);
  }

  private static HttpEntity executeRemoteContent(final HttpClient client, final HttpUriRequest request) throws ClientProtocolException, IOException,
  ParseException, HttpException {
    return executeRemoteContent(client, request, null);
  }

  private static HttpEntity executeRemoteContent(final HttpClient client, final HttpUriRequest request, final HttpClientContext localContext)
      throws ClientProtocolException, IOException, ParseException, HttpException {
    final HttpResponse httpResponse = client.execute(request);

    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      return httpResponse.getEntity();
    } else {
      throw new HttpException(httpResponse.getStatusLine().toString() + EntityUtils.toString(httpResponse.getEntity()));
    }
  }

  public static CloseableHttpClient buildProxyClient() {
    return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
  }
}