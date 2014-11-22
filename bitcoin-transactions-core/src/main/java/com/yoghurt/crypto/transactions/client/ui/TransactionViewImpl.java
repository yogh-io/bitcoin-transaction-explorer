package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.widget.TransactionInputWidget;
import com.yoghurt.crypto.transactions.client.widget.TransactionOutputWidget;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.client.widget.transaction.TransactionHexViewer;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;

public class TransactionViewImpl extends Composite implements TransactionView {
  interface TransactionViewImplUiBinder extends UiBinder<Widget, TransactionViewImpl> {}

  private static final TransactionViewImplUiBinder UI_BINDER = GWT.create(TransactionViewImplUiBinder.class);

  @UiField TransactionHexViewer transactionHexViewer;

  @UiField(provided = true)  ValueViewer txIdViewer;

  @UiField FlowPanel inputContainer;
  @UiField FlowPanel outputContainer;

  @UiField(provided = true) ValueViewer versionViewer;
  @UiField(provided = true) ValueViewer lockTimeViewer;

  @Inject
  public TransactionViewImpl() {
    txIdViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.TRANSACTION_HASH));
    versionViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.VERSION));
    lockTimeViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setTransactionData(final Transaction transaction, final RawTransactionContainer rawTransaction) {
    transactionHexViewer.setTransaction(rawTransaction);

    txIdViewer.setValue(transaction.getTransactionId());

    versionViewer.setValue(String.valueOf(transaction.getVersion()));
    lockTimeViewer.setValue(String.valueOf(transaction.getLockTime()));

    if (transaction.getInputs() != null) {
      for (final TransactionInput input : transaction.getInputs()) {
        final TransactionInputWidget inputWidget = new TransactionInputWidget(input);
        inputContainer.add(inputWidget);
      }
    }

    if (transaction.getOutputs() != null) {
      for (final TransactionOutput output : transaction.getOutputs()) {
        final TransactionOutputWidget inputWidget = new TransactionOutputWidget(output);
        outputContainer.add(inputWidget);
      }
    }
  }
}
