package com.yoghurt.crypto.transactions.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;

public interface BlockView extends BlockchainView, IsWidget {

  public interface Presenter {
    void loadTransactionList();
  }

  void setBlock(BlockInformation blockInformation);

  void setTransactionList(ArrayList<String> transactions);

  void setPresenter(Presenter presenter);
}
