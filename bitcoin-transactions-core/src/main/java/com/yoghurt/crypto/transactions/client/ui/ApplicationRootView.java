package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ApplicationRootView extends Composite implements AcceptsOneWidget {
  interface ApplicationRootViewUiBinder extends UiBinder<Widget, ApplicationRootView> {}

  private static final ApplicationRootViewUiBinder UI_BINDER = GWT.create(ApplicationRootViewUiBinder.class);

  @UiField SimplePanel contentPanel;
  @UiField Label title;

  @Inject
  public ApplicationRootView() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setWidget(final IsWidget w) {
    if(w == null) {
      return;
    }

    contentPanel.setWidget(w);
  }
}
