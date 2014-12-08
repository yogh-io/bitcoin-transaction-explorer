package com.yoghurt.crypto.transactions.server.servlets.util;

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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Wrapper to get remote content and return it as String.
 */
public class HttpClientProxy {
  private final static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5 * 1000).build();

  public static HttpEntity getRemoteContent(final HttpClient client, final String url) throws URISyntaxException, ClientProtocolException, IOException, ParseException, HttpException {
    final HttpResponse httpResponse = client.execute(new HttpGet(url));

    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      return httpResponse.getEntity();
    } else {
      throw new HttpException(url + " > " + httpResponse.getStatusLine().toString() + EntityUtils.toString(httpResponse.getEntity()));
    }
  }

  public static CloseableHttpClient buildProxyClient() {
    return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
  }
}