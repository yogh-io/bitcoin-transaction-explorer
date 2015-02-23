package com.yoghurt.crypto.transactions.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.yoghurt.crypto.transactions.client.place.MinePlace;
import com.yoghurt.crypto.transactions.client.resources.R;
import com.yoghurt.crypto.transactions.client.resources.SimpleColorPicker;
import com.yoghurt.crypto.transactions.client.ui.ApplicationRootView;
import com.yoghurt.crypto.transactions.client.ui.MineActivity;
import com.yoghurt.crypto.transactions.client.ui.MineViewImpl;

public class Application implements EntryPoint {

  @Override
  public void onModuleLoad() {
    R.init(new SimpleColorPicker());

    final ApplicationRootView appDisplay = new ApplicationRootView();

    final MineViewImpl view = new MineViewImpl();

    final MineActivity activity = new MineActivity(view, new MinePlace());

    RootPanel.get().add(appDisplay);

    activity.start(appDisplay, null);
  }
}
