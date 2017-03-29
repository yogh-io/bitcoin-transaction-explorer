package com.yoghurt.crypto.transactions.client.service;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.yoghurt.crypto.transactions.client.json.JSONParser;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;

public class TransactionInformationParseCallback extends ForwardedAsyncCallback<TransactionInformation, JSONValue> {

  public TransactionInformationParseCallback(AsyncCallback<TransactionInformation> callback) {
    super(callback);
  }

  @Override
  public void onSuccess(JSONValue result) {
    callback.onSuccess(JSONParser.parseTransactionInformation(result));
  }
}
