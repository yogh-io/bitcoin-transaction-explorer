package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.yoghurt.crypto.transactions.client.place.AddressPlaceRouter;
import com.yoghurt.crypto.transactions.client.resources.R;
import com.yoghurt.crypto.transactions.client.util.address.AddressEncodeUtil;
import com.yoghurt.crypto.transactions.client.util.address.Base58;
import com.yoghurt.crypto.transactions.shared.domain.Address;

public class AddressViewer extends ValueViewer {
  private final ClickHandler mouseClickHandler = new ClickHandler() {
    @Override
    public void onClick(final ClickEvent event) {
      presenter.goToAddress(value);
    }
  };
  private final AddressPlaceRouter presenter;

  public AddressViewer(final AddressPlaceRouter presenter) {
    super(R.color().address());

    this.presenter = presenter;

    setMouseClickHandler(mouseClickHandler);
  }

  public void setValue(final Address address) {
    super.setValue(Base58.encode(AddressEncodeUtil.encodeAddress(address)));
  }
}
