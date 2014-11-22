package com.yoghurt.crypto.transactions.client.di;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.yoghurt.crypto.transactions.client.Application;
import com.yoghurt.crypto.transactions.client.ui.ApplicationRootView;

@GinModules(value = ApplicationClientModule.class)
public interface ApplicationGinjector extends Ginjector {
  ApplicationGinjector INSTANCE = GWT.create(ApplicationGinjector.class);

  void inject(Application application);

  ApplicationRootView getApplicationRootView();
}
