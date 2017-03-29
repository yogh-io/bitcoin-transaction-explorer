package com.yoghurt.crypto.transactions.client.service;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

public final class RequestUtil {
  private static final int CLIENT_TIMEOUT = 15 * 1000;

  private RequestUtil() {
  }

  public static void doPost(final String url, String payload, final AsyncCallback<JSONValue> callback) {
    doRequest(url, RequestBuilder.POST, payload, callback);
  }

  public static void doGet(final String url, final AsyncCallback<JSONValue> callback) {
    doRequest(url, RequestBuilder.GET, null, callback);
  }

  private static void doRequest(String url, Method method, String payload, AsyncCallback<JSONValue> callback) {
    final RequestBuilder builder = new RequestBuilder(method, URL.encode(url));
    builder.setHeader("Content-Type", "application/json");
    builder.setHeader("Accept", "application/json");

    builder.setTimeoutMillis(CLIENT_TIMEOUT);
    try {
      builder.sendRequest(payload, new ServiceCallback(callback));
    } catch (final RequestException requestException) {
      callback.onFailure(requestException);
    }
  }
}
