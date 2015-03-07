package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.shared.domain.config.RetrievalHookConfig;

public interface ConfigView extends IsWidget, HasValue<RetrievalHookConfig> {

  public interface Presenter {

    void submitNewPassword(String pass);

    void checkAuthenticed(String pass);

    void saveConfig();

  }

  void setCreatePassword(boolean create);

  void setPresenter(Presenter presenter);

  void setAuthentic(boolean result);

}
