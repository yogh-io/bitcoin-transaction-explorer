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
import com.yoghurt.crypto.transactions.client.util.transaction.TransactionEncodeUtil;
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
import com.yoghurt.crypto.transactions.shared.domain.TransactionState;

public class TransactionViewImpl extends Composite implements TransactionView {
  interface TransactionViewImplUiBinder extends UiBinder<Widget, TransactionViewImpl> {}

  private static final TransactionViewImplUiBinder UI_BINDER = GWT.create(TransactionViewImplUiBinder.class);

  // Widgets relating to showing which content (error/info)
  @UiField FlowPanel errorView;
  @UiField Label transactionFullBlownErrorLabel;
  @UiField FlowPanel fullTransactionInformation;
  @UiField Label notFoundLabel;

  // General information
  @UiField HashHexViewer txIdViewer;

  // Widgets related to extra (blockchain information)
  @UiField FlowPanel extraInformationContainer;
  @UiField ValueViewer txStateViewer;
  @UiField LabelledWidget txBlockContainer;
  @UiField(provided = true) BlockViewer txBlockViewer;
  @UiField LabelledWidget txConfirmationsContainer;
  @UiField ValueViewer txConfirmationsViewer;
  @UiField LabelledWidget txTimeContainer;
  @UiField ValueViewer txTimeViewer;

  // Input/output containers
  @UiField FlowPanel inputContainer;
  @UiField FlowPanel outputContainer;

  // Misc info
  @UiField ValueViewer txVersionViewer;
  @UiField ValueViewer txLockTimeViewer;

  // Raw hex
  @UiField TransactionHexViewer txHexViewer;

  private final BitcoinPlaceRouter router;

  @Inject
  public TransactionViewImpl(final BitcoinPlaceRouter router) {
    this.router = router;

    txBlockViewer = new BlockViewer(router);

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setTransaction(final Transaction transaction, final boolean transactionHasErrors) {
    errorView.setVisible(transactionHasErrors);
    fullTransactionInformation.setVisible(true);
    if (transactionHasErrors) {
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
      // Eat.
    }

    txHexViewer.setValue(rawTransaction);
  }

  @Override
  public void setBlockchainInformation(final TransactionInformation transactionInformation) {
    extraInformationContainer.setVisible(transactionInformation != null);

    if (transactionInformation == null) {
      notFoundLabel.setVisible(true);
    } else {
      txStateViewer.setValue(transactionInformation.getState().name());

      if (transactionInformation.getState() == TransactionState.CONFIRMED) {
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
