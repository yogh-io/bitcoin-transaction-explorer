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
import com.yoghurt.crypto.transactions.client.i18n.M;
import com.yoghurt.crypto.transactions.client.util.FormatUtil;
import com.yoghurt.crypto.transactions.client.util.TextConversionUtil;
import com.yoghurt.crypto.transactions.client.util.block.BlockEncodeUtil;
import com.yoghurt.crypto.transactions.client.util.transaction.TransactionEncodeUtil;
import com.yoghurt.crypto.transactions.client.widget.BitsTargetHexViewer;
import com.yoghurt.crypto.transactions.client.widget.BlockHexViewer;
import com.yoghurt.crypto.transactions.client.widget.BlockViewer;
import com.yoghurt.crypto.transactions.client.widget.HashHexViewer;
import com.yoghurt.crypto.transactions.client.widget.TextContextFactory;
import com.yoghurt.crypto.transactions.client.widget.TransactionHexViewer;
import com.yoghurt.crypto.transactions.client.widget.TransactionViewer;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.client.widget.VersionViewer;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;

import gwt.material.design.client.ui.MaterialButton;

@Singleton
public class BlockViewImpl extends AbstractBlockchainView implements BlockView {
  interface BlockViewImplUiBinder extends UiBinder<Widget, BlockViewImpl> {}

  private static final BlockViewImplUiBinder UI_BINDER = GWT.create(BlockViewImplUiBinder.class);

  @UiField FlowPanel extraInformationContainer;
  @UiField Label notFoundLabel;

  @UiField HashHexViewer blockHashViewer;
  @UiField BitsTargetHexViewer targetViewer;
  @UiField HashHexViewer coinbaseHashViewer;

  @UiField VersionViewer versionViewer;
  @UiField(provided = true) BlockViewer previousBlockHashViewer;
  @UiField ValueViewer merkleRootViewer;
  @UiField ValueViewer timestampViewer;
  @UiField ValueViewer bitsViewer;
  @UiField ValueViewer nonceViewer;

  @UiField ValueViewer heightViewer;
  @UiField ValueViewer numConfirmationsViewer;
  @UiField ValueViewer numTransactionsViewer;
  @UiField(provided = true) BlockViewer nextBlockViewer;

  @UiField ValueViewer blockWeightViewer;
  // @UiField ValueViewer blockVSizeViewer;
  @UiField ValueViewer blockBaseSizeViewer;
  @UiField ValueViewer totalSizeViewer;

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

    blockWeightViewer.setContextFactory(new TextContextFactory<String>(M.messages().blockWeightContext()));
    // txVSizeViewer.setContextFactory(new
    // TextContextFactory<String>(M.messages().blockVSizeContext()));
    blockBaseSizeViewer.setContextFactory(new TextContextFactory<String>(M.messages().blockBaseSizeContext()));
    totalSizeViewer.setContextFactory(new TextContextFactory<String>(M.messages().blockTotalSizeContext()));
  }

  @Override
  public void setPresenter(final Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void setBlock(final BlockInformation blockInformation) {
    try {
      final Transaction coinbase = getTransactionFromHex(blockInformation.getCoinbaseInformation().getRawHex());
      final Block block = getBlockFromHex(blockInformation.getRawBlockHeaders());

      final RawTransactionContainer rawTransaction = new RawTransactionContainer();
      final RawBlockContainer rawBlock = new RawBlockContainer();

      BlockEncodeUtil.encodeBlock(block, rawBlock);
      TransactionEncodeUtil.encodeTransaction(coinbase, rawTransaction);

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

      blockWeightViewer.setValue(blockInformation.getWeight() / 1000D);
      // blockVSizeViewer.setValue(Math.ceil(blockInformation.getWeight() / 4D) /
      // 1000D);
      blockBaseSizeViewer.setValue(blockInformation.getStrippedSize() / 1000D);
      totalSizeViewer.setValue(blockInformation.getSize() / 1000D);

      coinbaseInputViewer.setValue(TextConversionUtil.fromASCIIBytes(rawTransaction.find(TransactionPartType.COINBASE_SCRIPT_SIG).getValue()));

      transactionPanel.clear();
      loadTransactionsButton.setVisible(true);
    } catch (final Throwable e) {
      e.printStackTrace();
      // Eat.
    }
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
