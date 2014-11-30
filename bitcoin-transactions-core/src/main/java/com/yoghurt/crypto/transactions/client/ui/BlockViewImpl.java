package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.util.FormatUtil;
import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.widget.HashViewer;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;

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

  public BlockViewImpl() {
    blockHashViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));
    versionViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));
    previousBlockHashViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));
    merkleRootViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));
    timestampViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));
    bitsViewer = new HashViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));
    nonceViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));

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
  }

  @Override
  public void setBlockInformation(final BlockInformation transactionInformation) {

  }
}
