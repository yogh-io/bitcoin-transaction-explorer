package com.yoghurt.crypto.transactions.client.di;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.yoghurt.crypto.transactions.client.place.ApplicationActivityMapper;
import com.yoghurt.crypto.transactions.client.place.ApplicationActivityMapper.ActivityFactory;
import com.yoghurt.crypto.transactions.client.place.ApplicationPlaceHistoryMapper;
import com.yoghurt.crypto.transactions.client.place.DefaultPlace;
import com.yoghurt.crypto.transactions.client.place.StartupPlace;
import com.yoghurt.crypto.transactions.client.ui.StartupView;
import com.yoghurt.crypto.transactions.client.ui.StartupViewImpl;
import com.yoghurt.crypto.transactions.client.ui.TransactionView;
import com.yoghurt.crypto.transactions.client.ui.TransactionViewImpl;

public class ApplicationClientModule extends AbstractGinModule {
  @Override
  protected void configure() {
    // Binding application critical architecure
    bind(ActivityMapper.class).to(ApplicationActivityMapper.class).in(Singleton.class);;
    bind(Place.class).annotatedWith(DefaultPlace.class).to(StartupPlace.class).in(Singleton.class);
    bind(PlaceController.class).to(ApplicationPlaceController.class).in(Singleton.class);
    bind(PlaceHistoryMapper.class).to(ApplicationPlaceHistoryMapper.class).in(Singleton.class);
    bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

    // Binding views
    bind(StartupView.class).to(StartupViewImpl.class).in(Singleton.class);
    bind(TransactionView.class).to(TransactionViewImpl.class);

    install(new GinFactoryModuleBuilder().build(ActivityFactory.class));
  }

  public static class ApplicationPlaceController extends PlaceController {
    @Inject
    public ApplicationPlaceController(final EventBus eventBus) {
      super(eventBus);
    }
  }
}
