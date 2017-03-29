package com.yoghurt.crypto.transactions.client.service;

import org.apache.http.HttpStatus;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.yoghurt.crypto.transactions.shared.domain.ApplicationException;
import com.yoghurt.crypto.transactions.shared.domain.ApplicationException.Reason;

public final class ServiceCallback implements RequestCallback {
  private final AsyncCallback<JSONValue> callback;

  public ServiceCallback(AsyncCallback<JSONValue> callback) {
    this.callback = callback;
  }

  @Override
  public void onResponseReceived(final Request request, final Response response) {
    try {
      if (response.getStatusCode() == HttpStatus.SC_OK) {
        callback.onSuccess(JSONParser.parseStrict(response.getText()));
      } else {
        callback.onFailure(new ApplicationException(Reason.INTERNAL_ERROR));
      }
    } catch (final Exception e) {
      callback.onFailure(e);
    }
  }

  @Override
  public void onError(final Request request, final Throwable exception) {
    callback.onFailure(exception);
  }

}