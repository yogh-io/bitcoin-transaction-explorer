package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;

public interface BlockView extends BlockchainView, IsWidget {

  public interface Presenter {

  }

  void setBlock(BlockInformation blockInformation);
}
