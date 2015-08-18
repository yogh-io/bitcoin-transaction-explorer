package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.client.util.address.Base58CheckContents;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;

public interface AddressView extends IsWidget {
  public interface Presenter {

  }

  void setAddress(Base58CheckContents address);

  void setAddressInformation(AddressInformation addressInformation);
}
