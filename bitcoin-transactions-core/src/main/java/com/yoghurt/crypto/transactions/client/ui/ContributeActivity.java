package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.yoghurt.crypto.transactions.shared.domain.config.UserApplicationConfig;

public class ContributeActivity  extends AbstractActivity implements ContributeView.Presenter {

  private final ContributeView view;
  private final UserApplicationConfig appConfig;

  @Inject
  public ContributeActivity(final ContributeView view, final UserApplicationConfig appConfig) {
    this.view = view;
    this.appConfig = appConfig;
  }

  @Override
  public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
    panel.setWidget(view);

    view.setConfig(appConfig);
  }
}
