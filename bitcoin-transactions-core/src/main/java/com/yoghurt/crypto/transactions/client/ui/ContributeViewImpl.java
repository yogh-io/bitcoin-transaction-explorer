package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ContributeViewImpl extends Composite implements ContributeView {
  interface ContributeViewImplUiBinder extends UiBinder<Widget, ContributeViewImpl> {}

  private static final ContributeViewImplUiBinder UI_BINDER = GWT.create(ContributeViewImplUiBinder.class);

  public ContributeViewImpl() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }
}
