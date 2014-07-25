package com.yoghurt.crypto.transactions.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.yoghurt.crypto.transactions.client.di.ApplicationGinjector;

public class Application implements EntryPoint {
  @Override
  public void onModuleLoad() {
    ApplicationGinjector.INSTANCE.inject(this);

    RootPanel.get().add(ApplicationGinjector.INSTANCE.getApplicationRootView());
  }
}
