package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.yoghurt.crypto.transactions.client.place.AddressPlace;
import com.yoghurt.crypto.transactions.client.util.address.AddressParseUtil;
import com.yoghurt.crypto.transactions.client.util.address.Base58CheckContents;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;

public class AddressActivity extends LookupActivity<Base58CheckContents, AddressPlace> implements AddressView.Presenter {
  private final AddressView view;

  @Inject
  public AddressActivity(final AddressView view, @Assisted final AddressPlace place, final BlockchainRetrievalServiceAsync service) {
    super(place, service);
    this.view = view;
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final Base58CheckContents address) {
    panel.setWidget(view);

    if(address == null) {
      return;
    }

    view.setAddress(address);
  }

  @Override
  protected boolean mustPerformLookup(final AddressPlace place) {
    return false;
  }

  @Override
  protected Base58CheckContents createInfo(final AddressPlace place) {
    return AddressParseUtil.parseAddress(place.getPayload());
  }

  @Override
  protected void doLookup(final AddressPlace place, final AsyncCallback<Base58CheckContents> callback) {

  }

  @Override
  protected void doDeferredError(final AcceptsOneWidget panel, final Throwable caught) {
    // Not supported
  }
}