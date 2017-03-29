package com.yoghurt.crypto.transactions.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class ForwardedAsyncCallback<F, C> implements AsyncCallback<C> {
  protected AsyncCallback<F> callback;

  public ForwardedAsyncCallback(AsyncCallback<F> callback) {
    this.callback = callback;
  }

  @Override
  public void onFailure(Throwable caught) {
    callback.onFailure(caught);
  }
}
