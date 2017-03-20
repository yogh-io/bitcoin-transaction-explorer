package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.client.domain.Block;
import com.yoghurt.crypto.transactions.client.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.client.domain.RawTransactionContainer;

public interface MineView extends IsWidget {
  public interface Presenter {
    void startPoll();

    void pausePoll();

    void getLatestBlock();
  }

  void setPresenter(Presenter presenter);

  void setInformation(Block initialBlock, RawBlockContainer rawBlock, RawTransactionContainer coinbase, boolean custom);

  void broadcastLatestBlock(String latestBlock);

  void cancel();

}
