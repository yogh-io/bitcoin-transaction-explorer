package com.yoghurt.crypto.transactions.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ApplicationRootView extends Composite {
  interface ApplicationRootViewUiBinder extends UiBinder<Widget, ApplicationRootView> {
  }

  private static final ApplicationRootViewUiBinder UI_BINDER = GWT.create(ApplicationRootViewUiBinder.class);

  private final PlaceController placeController;

  @Inject
  public ApplicationRootView(final PlaceController placeController) {
    this.placeController = placeController;
    initWidget(UI_BINDER.createAndBindUi(this));
  }
}
