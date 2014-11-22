package com.yoghurt.crypto.transactions.server.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * Wrapper to get remote content and return it as String.
 */
public class HttpClientProxy {

  public static byte[] getRemoteContent(final HttpClient client, final String url) throws URISyntaxException, ClientProtocolException, IOException, ParseException, HttpException {
    final URI uri = new URI(url);
    final HttpGet getRequest = new HttpGet(uri);
    final HttpResponse httpResponse = client.execute(getRequest);

    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      return EntityUtils.toByteArray(httpResponse.getEntity());
    } else {
      throw new HttpException(httpResponse.getStatusLine().toString() + EntityUtils.toString(httpResponse.getEntity()));
    }
  }
}