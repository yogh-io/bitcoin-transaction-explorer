package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.util.BlockPartColorPicker;
import com.yoghurt.crypto.transactions.client.util.FormatUtil;
import com.yoghurt.crypto.transactions.client.widget.BlockHexViewer;
import com.yoghurt.crypto.transactions.client.widget.HashViewer;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.BlockPartType;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.shared.util.block.BlockEncodeUtil;

public class BlockViewImpl extends Composite implements BlockView {
  interface BlockViewImplUiBinder extends UiBinder<Widget, BlockViewImpl> {}

  private static final BlockViewImplUiBinder UI_BINDER = GWT.create(BlockViewImplUiBinder.class);

  @UiField(provided = true) ValueViewer blockHashViewer;

  @UiField(provided = true) ValueViewer versionViewer;
  @UiField(provided = true) ValueViewer previousBlockHashViewer;
  @UiField(provided = true) ValueViewer merkleRootViewer;
  @UiField(provided = true) ValueViewer timestampViewer;
  @UiField(provided = true) HashViewer bitsViewer;
  @UiField(provided = true) ValueViewer nonceViewer;

  @UiField BlockHexViewer blockHexViewer;

  public BlockViewImpl() {
    blockHashViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.PREV_BLOCK_HASH));
    versionViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.VERSION));
    previousBlockHashViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.PREV_BLOCK_HASH));
    merkleRootViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.MERKLE_ROOT));
    timestampViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.TIMESTAMP));
    bitsViewer = new HashViewer(BlockPartColorPicker.getFieldColor(BlockPartType.BITS));
    nonceViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.NONCE));

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setBlock(final Block block) {
    blockHashViewer.setValue(Str.toString(Hex.encode(block.getBlockHash())).toUpperCase());
    versionViewer.setValue(block.getVersion());

    previousBlockHashViewer.setValue(Str.toString(Hex.encode(block.getPreviousBlockHash())).toUpperCase());
    merkleRootViewer.setValue(Str.toString(Hex.encode(block.getMerkleRoot())).toUpperCase());

    timestampViewer.setValue(FormatUtil.formatDateTime(block.getTimestamp()));
    bitsViewer.setValue(block.getBits());
    nonceViewer.setValue(block.getNonce());

    final RawBlockContainer rawTransaction = new RawBlockContainer();
    try {
      BlockEncodeUtil.encodeBlock(block, rawTransaction);
    } catch (final Throwable e) {
      e.printStackTrace();
      // Eat.
    }

    blockHexViewer.setBlock(rawTransaction);
  }

  @Override
  public void setBlockInformation(final BlockInformation transactionInformation) {

  }
}
