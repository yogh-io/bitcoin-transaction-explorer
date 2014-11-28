package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;
import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.widget.BlockViewer;
import com.yoghurt.crypto.transactions.client.widget.TransactionInputWidget;
import com.yoghurt.crypto.transactions.client.widget.TransactionOutputWidget;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.client.widget.transaction.TransactionHexViewer;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;
import com.yoghurt.crypto.transactions.shared.util.transaction.TransactionEncodeUtil;

public class TransactionViewImpl extends Composite implements TransactionView {
  interface TransactionViewImplUiBinder extends UiBinder<Widget, TransactionViewImpl> {}

  private static final TransactionViewImplUiBinder UI_BINDER = GWT.create(TransactionViewImplUiBinder.class);

  private static final DateTimeFormat DATE_TIME_FORMATTER = DateTimeFormat.getFormat("MMMM dd, yyyy, HH:mm:ss Z");

  @UiField(provided = true) ValueViewer txIdViewer;
  @UiField Label loadPlaceHolder;
  @UiField Label notFoundLabel;
  @UiField FlowPanel extraInformationContainer;
  @UiField(provided = true) ValueViewer txStateViewer;
  @UiField(provided = true) BlockViewer txBlockViewer;
  @UiField(provided = true) ValueViewer txConfirmationsViewer;
  @UiField(provided = true) ValueViewer txTimeViewer;

  @UiField FlowPanel inputContainer;
  @UiField FlowPanel outputContainer;

  @UiField TransactionHexViewer txHexViewer;

  @UiField(provided = true) ValueViewer txVersionViewer;
  @UiField(provided = true) ValueViewer txLockTimeViewer;

  private final BitcoinPlaceRouter router;

  @Inject
  public TransactionViewImpl(final BitcoinPlaceRouter router) {
    this.router = router;

    txIdViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.TRANSACTION_HASH));
    txVersionViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.VERSION));
    txLockTimeViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));

    txStateViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));
    txBlockViewer = new BlockViewer(router);
    txConfirmationsViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));
    txTimeViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setTransaction(final Transaction transaction) {
    txIdViewer.setValue(transaction.getTransactionId());

    txVersionViewer.setValue(String.valueOf(transaction.getVersion()));
    txLockTimeViewer.setValue(String.valueOf(transaction.getLockTime()));

    if (transaction.getInputs() != null) {
      for (final TransactionInput input : transaction.getInputs()) {
        final TransactionInputWidget inputWidget = new TransactionInputWidget(input, router);
        inputContainer.add(inputWidget);
      }
    }

    if (transaction.getOutputs() != null) {
      for (final TransactionOutput output : transaction.getOutputs()) {
        final TransactionOutputWidget inputWidget = new TransactionOutputWidget(output);
        outputContainer.add(inputWidget);
      }
    }

    // Lastly, encode the transaction (back) to hex and display it.
    final RawTransactionContainer rawTransaction = new RawTransactionContainer();
    try {
      TransactionEncodeUtil.encodeTransaction(transaction, rawTransaction);
    } catch (final Throwable e) {
      e.printStackTrace();
      // Eat.
    }

    txHexViewer.setTransaction(rawTransaction);
  }

  @Override
  public void setBlockchainInformation(final TransactionInformation transactionInformation) {
    loadPlaceHolder.removeFromParent();

    if (transactionInformation == null) {
      notFoundLabel.setVisible(true);
      return;
    }

    extraInformationContainer.setVisible(true);

    txStateViewer.setValue(transactionInformation.getState().name());
    txBlockViewer.setValue(transactionInformation.getBlockHeight());
    txConfirmationsViewer.setValue(transactionInformation.getConfirmations());
    txTimeViewer.setValue(DATE_TIME_FORMATTER.format(transactionInformation.getTime()));
  }
}
