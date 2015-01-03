package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yoghurt.crypto.transactions.client.i18n.M;
import com.yoghurt.crypto.transactions.client.place.ApplicationPlace;
import com.yoghurt.crypto.transactions.client.place.StartupPlace;
import com.yoghurt.crypto.transactions.client.util.PlaceTokenParseUtil;

@Singleton
public class ApplicationRootView extends Composite implements AcceptsOneWidget {
  interface ApplicationRootViewUiBinder extends UiBinder<Widget, ApplicationRootView> {}

  private static final ApplicationRootViewUiBinder UI_BINDER = GWT.create(ApplicationRootViewUiBinder.class);

  @UiField SimplePanel contentPanel;
  @UiField Anchor title;
  @UiField TextBox lookupField;

  private final PlaceController placeController;

  @Inject
  public ApplicationRootView(final PlaceHistoryMapper historyMapper, final PlaceController placeController) {
    this.placeController = placeController;

    initWidget(UI_BINDER.createAndBindUi(this));

    title.setHref("#" + historyMapper.getToken(new StartupPlace()));

    lookupField.getElement().setPropertyString("placeholder", M.messages().applicationLookupFieldPlaceHolder());
    lookupField.setFocus(true);
  }

  @UiHandler("lookupField")
  public void onLookupKeyUp(final KeyDownEvent event) {
    if (event.getNativeKeyCode() != KeyCodes.KEY_ENTER) {
      return;
    }

    final ApplicationPlace place = PlaceTokenParseUtil.parseToken(lookupField.getText());
    if(place == null) {
      // Do something, like show a mild error.
      return;
    }

    placeController.goTo(place);
  }

  @Override
  public void setWidget(final IsWidget w) {
    if(w == null) {
      return;
    }

    contentPanel.setWidget(w);
  }
}
