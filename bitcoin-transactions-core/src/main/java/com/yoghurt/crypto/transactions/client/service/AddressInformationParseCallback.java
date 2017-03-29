package com.yoghurt.crypto.transactions.client.service;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.yoghurt.crypto.transactions.client.json.JSONParser;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;

public class AddressInformationParseCallback extends ForwardedAsyncCallback<AddressInformation, JSONValue> {

  public AddressInformationParseCallback(AsyncCallback<AddressInformation> callback) {
    super(callback);
  }

  @Override
  public void onSuccess(JSONValue result) {
    callback.onSuccess(JSONParser.parseAddressInformation(result));
  }
}
