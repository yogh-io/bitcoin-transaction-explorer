package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.yoghurt.crypto.transactions.client.domain.transaction.RawTransactionContainer;
import com.yoghurt.crypto.transactions.client.domain.transaction.Transaction;
import com.yoghurt.crypto.transactions.client.domain.transaction.TransactionInput;
import com.yoghurt.crypto.transactions.client.widget.LabelledValue;
import com.yoghurt.crypto.transactions.client.widget.TransactionHexViewer;
import com.yoghurt.crypto.transactions.client.widget.TransactionInputWidget;

public class TransactionBreakdownViewImpl extends Composite implements TransactionBreakdownView {
  interface TransactionBreakdownViewImplUiBinder extends UiBinder<Widget, TransactionBreakdownViewImpl> {}

  private static final TransactionBreakdownViewImplUiBinder UI_BINDER = GWT.create(TransactionBreakdownViewImplUiBinder.class);

  @UiField TransactionHexViewer transactionHexViewer;

  @UiField FlowPanel inputContainer;

  @UiField LabelledValue versionField;
  @UiField LabelledValue lockTimeField;

  @Inject
  public TransactionBreakdownViewImpl() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setTransactionData(final Transaction transaction, final RawTransactionContainer rawTransaction) {
    transactionHexViewer.setTransaction(rawTransaction);

    versionField.setValue(String.valueOf(transaction.getVersion()));
    lockTimeField.setValue(String.valueOf(transaction.getLockTime()));

    for(final TransactionInput input : transaction.getInputs()) {
      final TransactionInputWidget inputWidget = new TransactionInputWidget(input);
      inputContainer.add(inputWidget);
    }
  }
}
