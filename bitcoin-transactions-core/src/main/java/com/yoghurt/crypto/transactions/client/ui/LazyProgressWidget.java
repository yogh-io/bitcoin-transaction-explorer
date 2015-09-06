package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;

public interface LazyProgressWidget extends IsWidget {
  public interface LazyProgressListener {
    void progressComplete();
  }

  void subscribeProgressListener(LazyProgressListener applicationRootView);
}
