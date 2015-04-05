package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class ContributeActivity  extends AbstractActivity implements ContributeView.Presenter {

  private final ContributeView view;

  @Inject
  public ContributeActivity(final ContributeView view) {
    this.view = view;
  }

  @Override
  public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
    panel.setWidget(view);
  }
}
