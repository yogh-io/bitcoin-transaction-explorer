package com.yoghurt.crypto.transactions.client.ui;

import gwt.material.design.client.ui.MaterialProgress;
import gwt.material.design.client.ui.MaterialTextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yoghurt.crypto.transactions.client.place.ApplicationPlace;
import com.yoghurt.crypto.transactions.client.place.ContributePlace;
import com.yoghurt.crypto.transactions.client.place.StartupPlace;
import com.yoghurt.crypto.transactions.client.ui.LazyProgressWidget.LazyProgressListener;
import com.yoghurt.crypto.transactions.client.util.PlaceTokenParseUtil;
import com.yoghurt.crypto.transactions.client.widget.HeadingWidget;
import com.yoghurt.crypto.transactions.shared.domain.config.UserApplicationConfig;

@Singleton
public class ApplicationRootView extends Composite implements AcceptsOneWidget, LazyProgressListener {
  interface ApplicationRootViewUiBinder extends UiBinder<Widget, ApplicationRootView> {}

  private static final ApplicationRootViewUiBinder UI_BINDER = GWT.create(ApplicationRootViewUiBinder.class);

  @UiField FlowPanel header;
  @UiField HeadingWidget applicationTitle;
  @UiField HeadingWidget applicationSubTitle;
  @UiField SimplePanel contentPanel;
  @UiField Anchor contributeLink;
  @UiField MaterialTextBox lookupField;

  @UiField SimplePanel progressContainer;
  @UiField MaterialProgress progress;

  private final PlaceController placeController;

  @Inject
  public ApplicationRootView(final PlaceHistoryMapper historyMapper, final PlaceController placeController, final UserApplicationConfig appConfig) {
    this.placeController = placeController;

    initWidget(UI_BINDER.createAndBindUi(this));

    applicationTitle.setText(appConfig.getApplicationTitle());
    applicationSubTitle.setText(appConfig.getApplicationSubTitle());

    contributeLink.setHref("#" + historyMapper.getToken(new ContributePlace()));
  }

  @UiHandler("applicationTitle")
  public void onTitleClick(final ClickEvent e) {
    placeController.goTo(new StartupPlace());
  }

  @UiHandler("lookupField")
  public void onLookupKeyUp(final KeyUpEvent event) {
    if (event.getNativeKeyCode() != KeyCodes.KEY_ENTER) {
      return;
    }

    final ApplicationPlace place = PlaceTokenParseUtil.parseToken(lookupField.getText());
    if (place == null) {
      // Do something, like show a mild error.
      return;
    }

    placeController.goTo(place);
  }

  @Override
  public void setWidget(final IsWidget w) {
    if (w == null) {
      if (!progress.isAttached()) {
        progressContainer.setWidget(progress);
      }
      return;
    }

    if (w instanceof LazyProgressWidget) {
      ((LazyProgressWidget) w).subscribeProgressListener(this);
    } else {
      progressComplete();
    }

    contentPanel.setWidget(w);
  }

  @Override
  public void progressComplete() {
    progress.removeFromParent();
  }
}
