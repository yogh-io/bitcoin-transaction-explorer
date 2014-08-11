package com.yoghurt.crypto.transactions.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
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
  @UiField Anchor title;
  @UiField TextBox lookupField;

  @Inject
  public ApplicationRootView(final PlaceHistoryMapper historyMapper) {
    initWidget(UI_BINDER.createAndBindUi(this));

    title.setHref("#" + historyMapper.getToken(new StartupPlace()));

    lookupField.getElement().setPropertyString("placeholder", "Insert tx to look up, or insert raw transaction hex");
    lookupField.setFocus(true);
  }

  @Override
  public void setWidget(final IsWidget w) {
    contentPanel.clear();

    if (w != null) {
      contentPanel.add(w);
    }
  }
}
