package com.yoghurt.crypto.transactions.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.yoghurt.crypto.transactions.client.di.ApplicationGinjector;
import com.yoghurt.crypto.transactions.client.place.DefaultPlace;

public class Application implements EntryPoint {

  @Inject private EventBus eventBus;

  @Inject private PlaceController placeController;

  @Inject private ActivityMapper actvityMapper;

  @Inject private PlaceHistoryMapper placeHistoryMapper;

  @Inject @DefaultPlace Place defaultPlace;

  @Override
  public void onModuleLoad() {
    ApplicationGinjector.INSTANCE.inject(this);

    final ActivityManager appActivityManager = new ActivityManager(actvityMapper, eventBus);
    final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(placeHistoryMapper);

    final ApplicationRootView appDisplay = ApplicationGinjector.INSTANCE.getApplicationRootView();
    appActivityManager.setDisplay(appDisplay);

    historyHandler.register(placeController, eventBus, defaultPlace);
    historyHandler.handleCurrentHistory();

    RootPanel.get().add(appDisplay);
  }
}
