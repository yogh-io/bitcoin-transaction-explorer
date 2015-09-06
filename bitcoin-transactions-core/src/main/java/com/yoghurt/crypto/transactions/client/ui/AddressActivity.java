package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.yoghurt.crypto.transactions.client.place.AddressPlace;
import com.yoghurt.crypto.transactions.client.util.address.AddressEncodeUtil;
import com.yoghurt.crypto.transactions.client.util.address.AddressParseUtil;
import com.yoghurt.crypto.transactions.client.util.address.Base58;
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
    address = AddressParseUtil.parseAddress(place.getPayload());

    panel.setWidget(view);

    if(address == null) {
      return;
    }

    view.setAddress(address);

    super.start(panel, eventBus);
  }

  @Override
  protected void doLookup(final AddressPlace place, final AsyncCallback<AddressInformation> callback) {
    GWT.log("Gonna ask server for things..");;
    if(!AddressParseUtil.isValid(address)) {
      GWT.log("INVALID?!");
      return;
    }

    final String addressString = Base58.encode(AddressEncodeUtil.encodeAddress(address));
    service.getAddressInformation(addressString, callback);
  }

  @Override
  protected void doDeferredError(final AcceptsOneWidget panel, final Throwable caught) {
    // Not supported
    GWT.log("boohoo " + caught.getMessage());
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final AddressInformation info) {
    GWT.log("yay?");
    view.setAddressInformation(info);
  }
}