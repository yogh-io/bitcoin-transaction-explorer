package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class HexContextPanel extends Composite implements AcceptsOneWidget {

  interface HexContextPanelUiBinder extends UiBinder<Widget, HexContextPanel> {}

  private static final HexContextPanelUiBinder UI_BINDER = GWT.create(HexContextPanelUiBinder.class);

  @UiField SimplePanel contextPanel;

  public HexContextPanel() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setWidget(final IsWidget w) {
    contextPanel.setWidget(w);
  }
}
