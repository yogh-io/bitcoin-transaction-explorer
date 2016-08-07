package com.yoghurt.crypto.transactions.client.ui;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;
import com.yoghurt.crypto.transactions.client.util.FormatUtil;
import com.yoghurt.crypto.transactions.client.util.TextConversionUtil;
import com.yoghurt.crypto.transactions.client.util.block.BlockEncodeUtil;
import com.yoghurt.crypto.transactions.client.util.transaction.TransactionEncodeUtil;
import com.yoghurt.crypto.transactions.client.widget.BitsTargetHexViewer;
import com.yoghurt.crypto.transactions.client.widget.BlockHexViewer;
import com.yoghurt.crypto.transactions.client.widget.BlockViewer;
import com.yoghurt.crypto.transactions.client.widget.HashHexViewer;
import com.yoghurt.crypto.transactions.client.widget.TransactionHexViewer;
import com.yoghurt.crypto.transactions.client.widget.TransactionViewer;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;

import gwt.material.design.client.ui.MaterialButton;

@Singleton
public class BlockViewImpl extends AbstractBlockchainView implements BlockView {
  interface BlockViewImplUiBinder extends UiBinder<Widget, BlockViewImpl> {
  }

  private static final BlockViewImplUiBinder UI_BINDER = GWT.create(BlockViewImplUiBinder.class);

  @UiField FlowPanel extraInformationContainer;
  @UiField Label notFoundLabel;

  @UiField HashHexViewer blockHashViewer;
  @UiField BitsTargetHexViewer targetViewer;
  @UiField HashHexViewer coinbaseHashViewer;

  @UiField ValueViewer versionViewer;
  @UiField(provided = true) BlockViewer previousBlockHashViewer;
  @UiField ValueViewer merkleRootViewer;
  @UiField ValueViewer timestampViewer;
  @UiField ValueViewer bitsViewer;
  @UiField ValueViewer nonceViewer;

  @UiField ValueViewer heightViewer;
  @UiField ValueViewer numConfirmationsViewer;
  @UiField ValueViewer numTransactionsViewer;
  @UiField(provided = true) BlockViewer nextBlockViewer;
  @UiField ValueViewer sizeViewer;

  @UiField BlockHexViewer blockHexViewer;
  @UiField TransactionHexViewer coinbaseHexViewer;

  @UiField ValueViewer coinbaseInputViewer;

  @UiField MaterialButton loadTransactionsButton;
  @UiField FlowPanel transactionPanel;

  private final BitcoinPlaceRouter router;

  private Presenter presenter;

  @Inject
  public BlockViewImpl(final BitcoinPlaceRouter router) {
    this.router = router;

    nextBlockViewer = new BlockViewer(router);
    previousBlockHashViewer = new BlockViewer(router);

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setPresenter(final Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void setBlock(final BlockInformation blockInformation) {
    final Transaction coinbase = getTransactionFromHex(blockInformation.getCoinbaseInformation().getRawHex());
    final Block block = getBlockFromHex(blockInformation.getRawBlockHeaders());

    final RawTransactionContainer rawTransaction = new RawTransactionContainer();
    final RawBlockContainer rawBlock = new RawBlockContainer();

    try {
      BlockEncodeUtil.encodeBlock(block, rawBlock);
      TransactionEncodeUtil.encodeTransaction(coinbase, rawTransaction);
    } catch (final Throwable e) {
      e.printStackTrace();
      // Eat.
    }

    blockHashViewer.setHash(block.getBlockHash());
    targetViewer.setBits(block.getBits());
    coinbaseHashViewer.setHash(coinbase.getTransactionId());

    versionViewer.setValue(block.getVersion());
    previousBlockHashViewer.setValue(block.getPreviousBlockHash());
    merkleRootViewer.setValue(block.getMerkleRoot());
    timestampViewer.setValue(FormatUtil.formatDateTime(block.getTimestamp()));
    bitsViewer.setValue(block.getBits());
    nonceViewer.setValue(block.getNonce());

    blockHexViewer.setValue(rawBlock.entrySet());
    notFoundLabel.setVisible(blockInformation == null);
    extraInformationContainer.setVisible(blockInformation != null);

    coinbaseHexViewer.setValue(rawTransaction);

    heightViewer.setValue(blockInformation.getHeight());
    numConfirmationsViewer.setValue(blockInformation.getNumConfirmations());
    numTransactionsViewer.setValue(blockInformation.getNumTransactions());
    nextBlockViewer.setValue(blockInformation.getNextBlockHash().toUpperCase());
    sizeViewer.setValue(blockInformation.getSize());

    coinbaseInputViewer.setValue(
        TextConversionUtil.fromASCIIBytes(rawTransaction.find(TransactionPartType.COINBASE_SCRIPT_SIG).getValue()));

    transactionPanel.clear();
    loadTransactionsButton.setVisible(true);
  }

  @UiHandler("loadTransactionsButton")
  public void onTransactionListClick(final ClickEvent e) {
    presenter.loadTransactionList();
    loadTransactionsButton.setVisible(false);
  }

  @Override
  public void setTransactionList(final ArrayList<String> transactions) {
    for (final String txid : transactions) {
      final TransactionViewer hashViewer = new TransactionViewer(router, false, false);
      transactionPanel.add(hashViewer);

      hashViewer.setValue(Hex.decode(txid));
    }
  }
}
