package com.yoghurt.crypto.transactions.client.ui;

import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.domain.Base58CheckContents;

public interface AddressView extends LazyProgressWidget {
  public interface Presenter {

  }

  void setAddress(Base58CheckContents address);

  void setAddressInformation(AddressInformation addressInformation);
}
