package com.yoghurt.crypto.transactions.client.ui;

import com.yoghurt.crypto.transactions.shared.domain.Block;

public interface MineView {
  public interface Presenter {

  }

  void setBlock(Block block);
}
