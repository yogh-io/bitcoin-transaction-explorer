package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.place.StartupPlace;
import com.yoghurt.crypto.transactions.client.util.AppAsyncCallback;
import com.yoghurt.crypto.transactions.client.util.transaction.ComputeUtil;
import com.yoghurt.crypto.transactions.shared.domain.config.RetrievalHookConfig;
import com.yoghurt.crypto.transactions.shared.service.ConfigServiceAsync;

public class ConfigActivity extends AbstractActivity implements ConfigView.Presenter {
  private static final String SALT = "$3cr3ts4ltedv4lu3t0them4xx";
  private final ConfigView view;
  private final ConfigServiceAsync service;
  private String hashedPassword;
  private final PlaceController placeController;

  @Inject
  public ConfigActivity(final ConfigView view, final ConfigServiceAsync service, final PlaceController placeController) {
    this.view = view;
    this.service = service;
    this.placeController = placeController;
  }

  @Override
  public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
    view.setPresenter(this);

    service.isPasswordPresent(new AppAsyncCallback<Boolean>() {
      @Override
      public void onSuccess(final Boolean result) {
        panel.setWidget(view);

        view.setCreatePassword(!result);
      }
    });
  }

  @Override
  public void submitNewPassword(final String password) {
    final String hashedPassword = getHashedPassword(password);

    service.updateServicePassword("", hashedPassword, new AppAsyncCallback<Void>() {
      @Override
      public void onSuccess(final Void result) {
        view.setCreatePassword(false);
      }
    });
  }

  @Override
  public void checkAuthenticed(final String password) {
    hashedPassword = getHashedPassword(password);

    service.isAuthentic(hashedPassword, new AppAsyncCallback<Boolean>() {
      @Override
      public void onSuccess(final Boolean result) {
        if(result) {
          service.getCurrentConfig(hashedPassword, new AppAsyncCallback<RetrievalHookConfig>() {

            @Override
            public void onSuccess(final RetrievalHookConfig config) {
              view.setAuthentic(result);
              view.setValue(config);
            }
          });
        }
      }
    });
  }

  private String getHashedPassword(final String password) {
    return Str.toString(Hex.encode(ComputeUtil.computeSHA256((SALT + password).getBytes())));
  }

  public void autoConfig(final RetrievalHookConfig config) {
    service.attemptAutoConfig(hashedPassword, config, new AppAsyncCallback<RetrievalHookConfig>() {
      @Override
      public void onSuccess(final RetrievalHookConfig result) {
        view.setValue(result);
      }
    });
  }

  @Override
  public void saveConfig() {
    service.setBlockchainHookConfig(hashedPassword, view.getValue(), new AppAsyncCallback<Void>() {
      @Override
      public void onSuccess(final Void result) {
        placeController.goTo(new StartupPlace());
      }
    });
  }
}
