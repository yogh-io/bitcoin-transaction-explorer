package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.domain.Base58CheckContents;

public interface AddressView extends IsWidget {
  public interface Presenter {

  }

  void setAddress(Base58CheckContents address);

  void setAddressInformation(AddressInformation addressInformation);
}
