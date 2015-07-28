package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.yoghurt.crypto.transactions.client.place.BlockPlace;
import com.yoghurt.crypto.transactions.client.place.BlockPlace.BlockDataType;
import com.yoghurt.crypto.transactions.client.place.ContributePlace;
import com.yoghurt.crypto.transactions.client.place.MinePlace;
import com.yoghurt.crypto.transactions.client.place.MinePlace.MineDataType;

public class StartupActivity extends AbstractActivity implements StartupView.Presenter {
  private final StartupView view;
  private final PlaceController placeController;

  @Inject
  public StartupActivity(final PlaceController placeController, final StartupView view) {
    this.placeController = placeController;
    this.view = view;

    view.setPresenter(this);
  }

  @Override
  public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
    panel.setWidget(view);
  }

  @Override
  public void onLastBlockClick() {
    placeController.goTo(new BlockPlace(BlockDataType.LAST));
  }

  @Override
  public void onMiningClick() {
    placeController.goTo(new MinePlace(MineDataType.LAST));
  }

  @Override
  public void onContributeClick() {
    placeController.goTo(new ContributePlace());
  }
}
