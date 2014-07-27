package com.yoghurt.crypto.transactions.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yoghurt.crypto.transactions.client.place.StartupPlace;

@Singleton
public class ApplicationRootView extends Composite implements AcceptsOneWidget {
  interface ApplicationRootViewUiBinder extends UiBinder<Widget, ApplicationRootView> { }

  private static final ApplicationRootViewUiBinder UI_BINDER = GWT.create(ApplicationRootViewUiBinder.class);

  @UiField FlowPanel contentPanel;
  @UiField Label title;
  @UiField TextBox lookupField;

  private final PlaceController placeController;

  @Inject
  public ApplicationRootView(final PlaceController placeController) {
    this.placeController = placeController;
    initWidget(UI_BINDER.createAndBindUi(this));

    lookupField.getElement().setPropertyString("placeholder", "Lookup txid, parse raw or parse encoded.");
    lookupField.setFocus(true);
  }

  @UiHandler("title")
  void onTitleClick(final ClickEvent e) {
    placeController.goTo(new StartupPlace());
  }

  @Override
  public void setWidget(final IsWidget w) {
    contentPanel.clear();

    if (w != null) {
      contentPanel.add(w);
    }
  }
}
