package com.yoghurt.crypto.transactions.client.util;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;

public abstract class AppAsyncCallback<T> implements AsyncCallback<TransactionInformation> {
  @Override
  public void onFailure(final Throwable caught) {
    throw new RuntimeException(caught);
  }
}
