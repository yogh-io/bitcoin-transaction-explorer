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
import com.yoghurt.crypto.transactions.client.resources.ColorPicker;
import com.yoghurt.crypto.transactions.client.resources.R;
import com.yoghurt.crypto.transactions.client.ui.ApplicationRootView;
import com.yoghurt.crypto.transactions.client.util.AppAsyncCallback;
import com.yoghurt.crypto.transactions.shared.domain.config.UserApplicationConfig;
import com.yoghurt.crypto.transactions.shared.service.ConfigServiceAsync;

public class Application implements EntryPoint {

  @Inject @DefaultPlace Place defaultPlace;

  @Inject private EventBus eventBus;

  @Inject private PlaceController placeController;

  @Inject private ActivityMapper actvityMapper;

  @Inject private PlaceHistoryMapper placeHistoryMapper;

  @Inject private ColorPicker colorPicker;

  @Inject private ApplicationConfigProvider configProvider;

  @Inject private ConfigServiceAsync configService;

  private ActivityManager appActivityManager;

  private PlaceHistoryHandler historyHandler;

  @Override
  public void onModuleLoad() {
    ApplicationGinjector.INSTANCE.inject(this);

    R.init(colorPicker);

    appActivityManager = new ActivityManager(actvityMapper, eventBus);
    historyHandler = new PlaceHistoryHandler(placeHistoryMapper);

    configService.getApplicationConfig(new AppAsyncCallback<UserApplicationConfig>() {
      @Override
      public void onSuccess(final UserApplicationConfig result) {
        configProvider.setApplicationConfig(result);

        onFinishedLoading();
      }
    });
  }

  public void onFinishedLoading() {
    final ApplicationRootView appDisplay = ApplicationGinjector.INSTANCE.getApplicationRootView();
    appActivityManager.setDisplay(appDisplay);

    historyHandler.register(placeController, eventBus, defaultPlace);
    historyHandler.handleCurrentHistory();

    RootPanel.get().add(appDisplay);
  }
}
