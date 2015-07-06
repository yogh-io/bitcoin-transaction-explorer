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
  private static final String SIMPLE_P2PKH = "01000000016d5412cdc802cee86b4f939ed7fc77c158193ce744f1117b5c6b67a4d70c046b010000006c493046022100be69797cf5d784412b1258256eb657c191a04893479dfa2ae5c7f2088c7adbe0022100e6b000bd633b286ed1b9bc7682fe753d9fdad61fbe5da2a6e9444198e33a670f012102f0e17f9afb1dca5ab9058b7021ba9fcbedecf4fac0f1c9e0fd96c4fdc200c1c2ffffffff0245a87edb080000001976a9147d4e6d55e1dffb0df85f509343451d170d14755188ac60e31600000000001976a9143bc576e6960a9d45201ba5087e39224d0a05a07988ac00000000";

  private static final String FIRST_EVER_TX = "f4184fc596403b9d638783cf57adfe4c75c605f6356fbc91338530e9831e9e16";

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
