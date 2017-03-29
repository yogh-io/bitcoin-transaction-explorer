package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.yoghurt.crypto.transactions.client.service.BlockchainRetrievalServiceAsync;

public abstract class LazyLookupActivity<E, P extends Place> extends AbstractActivity {
  protected final P place;
  protected final BlockchainRetrievalServiceAsync service;

  public LazyLookupActivity(final P place, final BlockchainRetrievalServiceAsync service) {
    this.place = place;
    this.service = service;
  }

  @Override
  public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
    doLookup(place, new AsyncCallback<E>() {
      @Override
      public void onSuccess(final E result) {
        doDeferredStart(panel, result);
      }

      @Override
      public void onFailure(final Throwable caught) {
        doDeferredError(panel, caught);
      }
    });
  }

  protected abstract void doLookup(final P place, final AsyncCallback<E> callback);

  /**
   * Deferredly start the activity after the information to be given to the concrete implementation has been determined.
   *
   * @param panel Panel in which content may live.
   * @param info Information for which content will be created.
   */
  protected abstract void doDeferredStart(final AcceptsOneWidget panel, final E info);

  /**
   * Defferedly error out because something went wrong.
   *
   * @param panel Panel in which content may live.
   * @param caught All information we're allowed to have about what may have gone wrong.
   */
  protected abstract void doDeferredError(AcceptsOneWidget panel, Throwable caught);

}
