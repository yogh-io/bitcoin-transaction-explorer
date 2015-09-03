package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.yoghurt.crypto.transactions.client.place.RPCResponsePlace;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;

public class RPCResponseActivity extends LookupActivity<String, RPCResponsePlace> {

  private final RPCResponseView view;

  @Inject
  public RPCResponseActivity(final RPCResponseView view, @Assisted final RPCResponsePlace place, final BlockchainRetrievalServiceAsync service) {
    super(place, service);

    this.view = view;
  }

  @Override
  protected void doLookup(final RPCResponsePlace place, final AsyncCallback<String> callback) {
    service.getJSONRPCResponse(place.getMethod(), place.getArguments(), callback);
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final String info) {
    panel.setWidget(view);

    view.setResponse(info);
  }

  @Override
  protected void doDeferredError(final AcceptsOneWidget panel, final Throwable caught) {
    GWT.log("Crapped out.");
  }

}
