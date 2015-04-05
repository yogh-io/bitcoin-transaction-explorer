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
import com.yoghurt.crypto.transactions.client.place.BlockPlace;
import com.yoghurt.crypto.transactions.client.place.BlockPlace.BlockDataType;
import com.yoghurt.crypto.transactions.client.place.DefaultPlace;
import com.yoghurt.crypto.transactions.client.place.ScriptPlace;
import com.yoghurt.crypto.transactions.client.place.StartupPlace;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace.TransactionDataType;
import com.yoghurt.crypto.transactions.client.resources.ColorPicker;
import com.yoghurt.crypto.transactions.client.resources.SimpleColorPicker;
import com.yoghurt.crypto.transactions.client.ui.BlockView;
import com.yoghurt.crypto.transactions.client.ui.BlockViewImpl;
import com.yoghurt.crypto.transactions.client.ui.ConfigView;
import com.yoghurt.crypto.transactions.client.ui.ConfigViewImpl;
import com.yoghurt.crypto.transactions.client.ui.ContributeView;
import com.yoghurt.crypto.transactions.client.ui.ContributeViewImpl;
import com.yoghurt.crypto.transactions.client.ui.MineView;
import com.yoghurt.crypto.transactions.client.ui.MineViewImpl;
import com.yoghurt.crypto.transactions.client.ui.ScriptView;
import com.yoghurt.crypto.transactions.client.ui.ScriptViewImpl;
import com.yoghurt.crypto.transactions.client.ui.StartupView;
import com.yoghurt.crypto.transactions.client.ui.StartupViewImpl;
import com.yoghurt.crypto.transactions.client.ui.TransactionView;
import com.yoghurt.crypto.transactions.client.ui.TransactionViewImpl;

public class ApplicationClientModule extends AbstractGinModule {
  @Override
  protected void configure() {
    // Binding application critical architecture
    bind(ActivityMapper.class).to(ApplicationActivityMapper.class).in(Singleton.class);;
    bind(Place.class).annotatedWith(DefaultPlace.class).to(StartupPlace.class).in(Singleton.class);
    bind(PlaceController.class).to(ApplicationPlaceController.class).in(Singleton.class);
    bind(BitcoinPlaceRouter.class).to(ApplicationPlaceController.class).in(Singleton.class);
    bind(PlaceHistoryMapper.class).to(ApplicationPlaceHistoryMapper.class).in(Singleton.class);
    bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
    bind(ColorPicker.class).to(SimpleColorPicker.class).in(Singleton.class);

    // Binding views
    bind(StartupView.class).to(StartupViewImpl.class).in(Singleton.class);
    bind(TransactionView.class).to(TransactionViewImpl.class);
    bind(BlockView.class).to(BlockViewImpl.class);
    bind(MineView.class).to(MineViewImpl.class);
    bind(ScriptView.class).to(ScriptViewImpl.class);
    bind(ConfigView.class).to(ConfigViewImpl.class);
    bind(ContributeView.class).to(ContributeViewImpl.class);

    install(new GinFactoryModuleBuilder().build(ActivityFactory.class));
  }

  public static class ApplicationPlaceController extends PlaceController implements BitcoinPlaceRouter {
    @Inject
    public ApplicationPlaceController(final EventBus eventBus) {
      super(eventBus);
    }

    @Override
    public void goToTransaction(final String txid) {
      goTo(new TransactionPlace(TransactionDataType.ID, txid));
    }

    @Override
    public void goToBlock(final int height) {
      goTo(new BlockPlace(BlockDataType.HEIGHT, height));
    }

    @Override
    public void goToBlock(final String blockHash) {
      goTo(new BlockPlace(BlockDataType.ID, blockHash));
    }

    @Override
    public void goToScript(final String transactionHash, final int outputIndex, final String scriptSig) {
      goTo(new ScriptPlace(transactionHash, outputIndex, scriptSig));
    }
  }
}
