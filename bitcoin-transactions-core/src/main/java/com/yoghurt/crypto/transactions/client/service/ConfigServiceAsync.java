package com.yoghurt.crypto.transactions.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.yoghurt.crypto.transactions.shared.domain.UserApplicationConfig;

public interface ConfigServiceAsync {
  void getApplicationConfig(AsyncCallback<UserApplicationConfig> callback);
}
