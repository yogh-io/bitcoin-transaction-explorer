package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.shared.domain.config.UserApplicationConfig;

public interface ContributeView extends IsWidget {
  public interface Presenter {

  }

  void setConfig(UserApplicationConfig appConfig);
}
