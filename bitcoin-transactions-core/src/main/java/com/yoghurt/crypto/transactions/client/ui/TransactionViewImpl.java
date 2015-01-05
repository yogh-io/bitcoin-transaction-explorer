package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;
import com.yoghurt.crypto.transactions.client.i18n.M;
import com.yoghurt.crypto.transactions.client.util.FormatUtil;
import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.widget.BlockViewer;
import com.yoghurt.crypto.transactions.client.widget.HashHexViewer;
import com.yoghurt.crypto.transactions.client.widget.LabelledWidget;
import com.yoghurt.crypto.transactions.client.widget.TransactionHexViewer;
import com.yoghurt.crypto.transactions.client.widget.TransactionInputWidget;
import com.yoghurt.crypto.transactions.client.widget.TransactionOutputWidget;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;
import com.yoghurt.crypto.transactions.shared.domain.TransactionState;
import com.yoghurt.crypto.transactions.shared.util.transaction.TransactionEncodeUtil;

public class TransactionViewImpl extends Composite implements TransactionView {
  interface TransactionViewImplUiBinder extends UiBinder<Widget, TransactionViewImpl> {}

  private static final TransactionViewImplUiBinder UI_BINDER = GWT.create(TransactionViewImplUiBinder.class);

  @UiField FlowPanel errorView;
  @UiField Label transactionFullBlownErrorLabel;

  @UiField FlowPanel fullTransactionInformation;

  @UiField(provided = true) HashHexViewer txIdViewer;
  @UiField Label notFoundLabel;
  @UiField FlowPanel extraInformationContainer;
  @UiField(provided = true) ValueViewer txStateViewer;
  @UiField LabelledWidget txBlockContainer;
  @UiField(provided = true) BlockViewer txBlockViewer;
  @UiField LabelledWidget txConfirmationsContainer;
  @UiField(provided = true) ValueViewer txConfirmationsViewer;
  @UiField LabelledWidget txTimeContainer;
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

    txIdViewer = new HashHexViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.TRANSACTION_HASH));
    txVersionViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.VERSION));
    txLockTimeViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));

    txStateViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));
    txBlockViewer = new BlockViewer(router);
    txConfirmationsViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));
    txTimeViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setTransaction(final Transaction transaction, final boolean transactionHasErrors) {
    errorView.setVisible(transactionHasErrors);
    fullTransactionInformation.setVisible(true);
    if(transactionHasErrors) {
      setErrorText(M.messages().transactionPlaceParseError());
    }

    txIdViewer.setHash(transaction.getTransactionId());

    txVersionViewer.setValue(transaction.getVersion());
    txLockTimeViewer.setValue(transaction.getLockTime());

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

    txHexViewer.resetContainer(rawTransaction);
  }

  @Override
  public void setBlockchainInformation(final TransactionInformation transactionInformation) {
    extraInformationContainer.setVisible(transactionInformation != null);

    if (transactionInformation == null) {
      notFoundLabel.setVisible(true);
    } else {
      txStateViewer.setValue(transactionInformation.getState().name());

      if(transactionInformation.getState() == TransactionState.CONFIRMED) {
        txBlockContainer.setVisible(true);
        txBlockViewer.setValue(transactionInformation.getBlockHash());
        txConfirmationsContainer.setVisible(true);
        txConfirmationsViewer.setValue(transactionInformation.getConfirmations());
        txTimeContainer.setVisible(true);
        txTimeViewer.setValue(FormatUtil.formatDateTime(transactionInformation.getTime()));
      } else {
        txTimeContainer.setVisible(false);
        txConfirmationsContainer.setVisible(false);
        txBlockContainer.setVisible(false);
      }
    }
  }

  @Override
  public void setError(final String hash, final Throwable caught) {
    txIdViewer.setHash(Hex.decode(hash));
    setErrorText(M.messages().transactionPlaceBlockchainExistenceNotFound());
    fullTransactionInformation.setVisible(false);
  }

  private void setErrorText(final String text) {
    errorView.setVisible(true);
    transactionFullBlownErrorLabel.setText(text);
  }
}
