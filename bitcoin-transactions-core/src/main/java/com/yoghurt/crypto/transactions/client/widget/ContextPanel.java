package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ContextPanel extends Composite implements AcceptsOneWidget {

  interface ContextPanelUiBinder extends UiBinder<Widget, ContextPanel> {}

  private static final ContextPanelUiBinder UI_BINDER = GWT.create(ContextPanelUiBinder.class);

  @UiField SimplePanel contextPanel;

  public ContextPanel() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setWidget(final IsWidget w) {
    contextPanel.setWidget(w);
  }
}
