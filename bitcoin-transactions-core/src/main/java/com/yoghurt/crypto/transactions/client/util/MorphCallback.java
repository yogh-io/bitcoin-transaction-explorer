package com.yoghurt.crypto.transactions.client.util;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class MorphCallback<E, T> implements AsyncCallback<E> {
  private final AsyncCallback<T> mirror;

  public MorphCallback(final AsyncCallback<T> mirror) {
    this.mirror = mirror;
  }

  @Override
  public void onFailure(final Throwable caught) {
    mirror.onFailure(caught);
  }

  @Override
  public void onSuccess(final E result) {
    mirror.onSuccess(morphResult(result));
  }

  protected abstract T morphResult(final E result);
}
