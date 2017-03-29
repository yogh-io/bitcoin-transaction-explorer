package com.yoghurt.crypto.transactions.client.ui;

import com.yoghurt.crypto.transactions.client.domain.Base58CheckContents;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;

public interface AddressView extends LazyProgressWidget {
  public interface Presenter {

  }

  void setAddress(Base58CheckContents address);

  void setAddressInformation(AddressInformation addressInformation);
}
