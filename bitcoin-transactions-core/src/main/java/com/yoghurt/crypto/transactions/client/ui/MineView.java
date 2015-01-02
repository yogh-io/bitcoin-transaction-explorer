package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;

public interface MineView extends IsWidget {
  public interface Presenter {
    void startPoll();

    void pausePoll();

    void getLatestBlock();
  }

  void setPresenter(Presenter presenter);

  void setInformation(Block block, RawBlockContainer rawBlock, boolean keepUpWithTip);

  void broadcastLatestBlock(String latestBlock);

  void cancel();
}
