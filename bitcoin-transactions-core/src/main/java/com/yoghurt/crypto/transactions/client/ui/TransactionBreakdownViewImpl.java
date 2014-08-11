package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yoghurt.crypto.transactions.client.domain.transaction.RawTransactionContainer;
import com.yoghurt.crypto.transactions.client.domain.transaction.Transaction;
import com.yoghurt.crypto.transactions.client.domain.transaction.TransactionInput;
import com.yoghurt.crypto.transactions.client.domain.transaction.TransactionOutput;
import com.yoghurt.crypto.transactions.client.domain.transaction.TransactionPartType;
import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.widget.TransactionInputWidget;
import com.yoghurt.crypto.transactions.client.widget.TransactionOutputWidget;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.client.widget.transaction.TransactionHexViewer;

@Singleton
public class TransactionBreakdownViewImpl extends Composite implements TransactionBreakdownView {
  interface TransactionBreakdownViewImplUiBinder extends UiBinder<Widget, TransactionBreakdownViewImpl> {}

  private static final TransactionBreakdownViewImplUiBinder UI_BINDER = GWT.create(TransactionBreakdownViewImplUiBinder.class);

  @UiField TransactionHexViewer transactionHexViewer;

  @UiField FlowPanel inputContainer;
  @UiField FlowPanel outputContainer;

  @UiField(provided = true) ValueViewer versionViewer;
  @UiField(provided = true) ValueViewer lockTimeViewer;

  @Inject
  public TransactionBreakdownViewImpl() {
    versionViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.VERSION));
    lockTimeViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.LOCK_TIME));

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setTransactionData(final Transaction transaction, final RawTransactionContainer rawTransaction) {
    transactionHexViewer.setTransaction(rawTransaction);

    versionViewer.setValue(String.valueOf(transaction.getVersion()));
    lockTimeViewer.setValue(String.valueOf(transaction.getLockTime()));

    inputContainer.clear();
    if (transaction.getInputs() != null) {
      for (final TransactionInput input : transaction.getInputs()) {
        final TransactionInputWidget inputWidget = new TransactionInputWidget(input);
        inputContainer.add(inputWidget);
      }
    }

    outputContainer.clear();
    if (transaction.getInputs() != null) {
      for (final TransactionOutput output : transaction.getOutputs()) {
        final TransactionOutputWidget inputWidget = new TransactionOutputWidget(output);
        outputContainer.add(inputWidget);
      }
    }
  }
}
