package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.util.BlockPartColorPicker;
import com.yoghurt.crypto.transactions.client.widget.BlockHexViewer;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.BlockPartType;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;
import com.yoghurt.crypto.transactions.shared.util.NumberParseUtil;
import com.yoghurt.crypto.transactions.shared.util.block.BlockEncodeUtil;
import com.yoghurt.crypto.transactions.shared.util.transaction.ComputeUtil;

public class MineViewImpl extends Composite implements MineView {
  interface MineViewImplUiBinder extends UiBinder<Widget, MineViewImpl> {}

  private static final MineViewImplUiBinder UI_BINDER = GWT.create(MineViewImplUiBinder.class);

  @UiField BlockHexViewer blockHexViewer;
  @UiField(provided = true) ValueViewer blockHashViewer;

  public MineViewImpl() {
    blockHashViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.PREV_BLOCK_HASH));

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setBlock(final Block initialBlock) {
    initialBlock.setNonce(initialBlock.getNonce() - 5);

    final RawBlockContainer rawBlock = new RawBlockContainer();
    try {
      BlockEncodeUtil.encodeBlock(initialBlock, rawBlock);
    } catch (final Throwable e) {
      e.printStackTrace();
      // Eat.
    }

    final RawBlockContainer viewBlock = rawBlock.copy();

    // Set up the viewblock for display
    blockHexViewer.setBlock(viewBlock);

    Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
      @Override
      public boolean execute() {
        long nonce = NumberParseUtil.parseUint32(rawBlock.getNonce());

        nonce += 1;

        rawBlock.setNonce(BlockEncodeUtil.encodeNonce(nonce));
        blockHexViewer.updateBlock(rawBlock);

        final byte[] computeDoubleSHA256 = ComputeUtil.computeDoubleSHA256(rawBlock.values());
        ArrayUtil.reverse(computeDoubleSHA256);

        blockHashViewer.setValue(computeDoubleSHA256);

        return true;
      }
    }, 1000);
  }
}
