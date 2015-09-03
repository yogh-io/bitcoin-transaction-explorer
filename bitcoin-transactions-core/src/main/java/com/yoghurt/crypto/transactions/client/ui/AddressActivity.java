package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.yoghurt.crypto.transactions.client.place.AddressPlace;
import com.yoghurt.crypto.transactions.client.util.address.AddressParseUtil;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.domain.Base58CheckContents;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;

public class AddressActivity extends LazyLookupActivity<AddressInformation, AddressPlace> implements AddressView.Presenter {
  private final AddressView view;

  private Base58CheckContents address;

  @Inject
  public AddressActivity(final AddressView view, @Assisted final AddressPlace place, final BlockchainRetrievalServiceAsync service) {
    super(place, service);
    this.view = view;
  }

  @Override
  public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
    super.start(panel, eventBus);

    address = AddressParseUtil.parseAddress(place.getPayload());

    panel.setWidget(view);

    if(address == null) {
      return;
    }

    view.setAddress(address);
  }

  @Override
  protected void doLookup(final AddressPlace place, final AsyncCallback<AddressInformation> callback) {
    if(!AddressParseUtil.isValid(address)) {
      return;
    }

    service.getAddressInformation(address, callback);
  }

  @Override
  protected void doDeferredError(final AcceptsOneWidget panel, final Throwable caught) {
    // Not supported
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final AddressInformation info) {
    // TODO Auto-generated method stub

  }
}