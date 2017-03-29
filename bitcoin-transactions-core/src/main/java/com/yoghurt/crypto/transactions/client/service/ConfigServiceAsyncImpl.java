package com.yoghurt.crypto.transactions.client.service;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.yoghurt.crypto.transactions.shared.domain.UserApplicationConfig;

public class ConfigServiceAsyncImpl implements ConfigServiceAsync {
  public ConfigServiceAsyncImpl() {
  }

  @Override
  public void getApplicationConfig(final AsyncCallback<UserApplicationConfig> callback) {
    RequestUtil.doGet("/application/config-retrieve", new ForwardedAsyncCallback<UserApplicationConfig, JSONValue>(callback) {
      @Override
      public void onSuccess(JSONValue result) {
        UserApplicationConfig config = new UserApplicationConfig();

        config.setApplicationTitle(result.isObject().get("applicationTitle").isString().stringValue());
        config.setApplicationSubTitle(result.isObject().get("applicationSubTitle").isString().stringValue());

        callback.onSuccess(config);
      }
    });
  }
}