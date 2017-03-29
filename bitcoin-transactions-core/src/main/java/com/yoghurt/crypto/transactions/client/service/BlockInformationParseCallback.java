package com.yoghurt.crypto.transactions.client.service;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.yoghurt.crypto.transactions.client.json.JSONParser;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;

public class BlockInformationParseCallback extends ForwardedAsyncCallback<BlockInformation, JSONValue> {
  public BlockInformationParseCallback(AsyncCallback<BlockInformation> callback) {
    super(callback);
  }

  @Override
  public void onSuccess(JSONValue result) {
    callback.onSuccess(JSONParser.parseBlockInformation(result));
  }
}
