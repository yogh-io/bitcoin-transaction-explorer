package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.yoghurt.crypto.transactions.client.place.BlockPlaceRouter;

/**
 * TODO Needs some attention. Because meh.
 */
public class BlockViewer extends ValueViewer {
  private final ClickHandler mouseClickHandler = new ClickHandler() {
    @Override
    public void onClick(final ClickEvent event) {
      if (blockHash != null) {
        router.goToBlock(blockHash);
      } else {
        router.goToBlock(blockHeight);
      }
    }
  };

  private final BlockPlaceRouter router;

  private int blockHeight;
  private String blockHash;

  public BlockViewer(final BlockPlaceRouter router) {
    super("View this block.");

    this.router = router;

    setMouseClickHandler(mouseClickHandler);
  }

  public void setValue(final int blockHeight) {
    this.blockHeight = blockHeight;

    super.setValue(blockHeight);
  }

  @Override
  public void setValue(final String blockHash) {
    this.blockHash = blockHash;

    super.setValue(blockHash);
  }
}
