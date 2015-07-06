package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;
import com.yoghurt.crypto.transactions.client.util.FormatUtil;
import com.yoghurt.crypto.transactions.client.util.block.BlockEncodeUtil;
import com.yoghurt.crypto.transactions.client.util.transaction.TransactionEncodeUtil;
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

@Singleton
public class BlockViewImpl extends AbstractBlockchainView implements BlockView {
  interface BlockViewImplUiBinder extends UiBinder<Widget, BlockViewImpl> {
  }

  private static final BlockViewImplUiBinder UI_BINDER = GWT.create(BlockViewImplUiBinder.class);

  @UiField FlowPanel extraInformationContainer;
  @UiField Label notFoundLabel;

  @UiField HashHexViewer blockHashViewer;
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

  // @UiField ValueViewer coinbaseInputViewer;

  @UiField FlowPanel transactionPanel;

  private final BitcoinPlaceRouter router;

  @Inject
  public BlockViewImpl(final BitcoinPlaceRouter router) {
    this.router = router;

    nextBlockViewer = new BlockViewer(router);
    previousBlockHashViewer = new BlockViewer(router);

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setBlock(final BlockInformation blockInformation) {
    final Transaction coinbase = getTransactionFromHex(blockInformation.getRawCoinbaseTransaction());
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

    new Timer() {
      @Override
      public void run() {
        transactionPanel.clear();
        if (blockInformation.getTransactions() != null) {
          for (final String txid : blockInformation.getTransactions()) {
            final TransactionViewer hashViewer = new TransactionViewer(router, false, false);
            transactionPanel.add(hashViewer);

            hashViewer.setValue(Hex.decode(txid));
          }
        }
      }
    }.schedule(400);
  }
}
