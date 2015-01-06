package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;

public interface BlockView extends IsWidget {

  public interface Presenter {

  }

  void setBlock(Block transaction, BlockInformation blockInformation, Transaction tx);
}
