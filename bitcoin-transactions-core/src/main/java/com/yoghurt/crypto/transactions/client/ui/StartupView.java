package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;

public interface StartupView extends IsWidget {
  public interface Presenter {

    void goToTest();

  }

  void setPresenter(Presenter presenter);
}
