package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.yoghurt.crypto.transactions.client.ui.StartupActivity;
import com.yoghurt.crypto.transactions.client.ui.TransactionActivity;

public class ApplicationActivityMapper implements ActivityMapper {

  @Inject private ActivityFactory factory;

  @Override
  public Activity getActivity(final Place place) {
    Activity presenter = null;

    if (place instanceof StartupPlace) {
      presenter = factory.createStartupPresenter((StartupPlace) place);
    } else if(place instanceof TransactionPlace) {
      presenter = factory.createTransactionBreakdownPresenter((TransactionPlace) place);
    }

    return presenter;
  }

  public interface ActivityFactory {
    StartupActivity createStartupPresenter(StartupPlace place);

    TransactionActivity createTransactionBreakdownPresenter(TransactionPlace place);
  }
}
