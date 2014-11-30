package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.yoghurt.crypto.transactions.client.ui.BlockPlaceRouter;
import com.yoghurt.crypto.transactions.client.util.misc.ColorBuilder;

public class BlockViewer extends ValueViewer {
  private final ClickHandler mouseClickHandler = new ClickHandler() {
    @Override
    public void onClick(final ClickEvent event) {
      presenter.goToBlock(blockHeight);
    }
  };

  private final BlockPlaceRouter presenter;

  private int blockHeight;

  public BlockViewer(final BlockPlaceRouter presenter) {
    super(ColorBuilder.interpret("darkgreen"), new SimpleContextFactory<String>() {
      @Override
      protected String getTextValue(final String value) {
        return "View this block.";
      }
    });

    this.presenter = presenter;

    setMouseClickHandler(mouseClickHandler);
  }

  public void setValue(final int blockHeight) {
    this.blockHeight = blockHeight;

    super.setValue(blockHeight);
  }

}
