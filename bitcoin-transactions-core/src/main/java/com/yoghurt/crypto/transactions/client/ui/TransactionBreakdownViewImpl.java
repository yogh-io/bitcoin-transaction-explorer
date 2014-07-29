package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.yoghurt.crypto.transactions.client.domain.transaction.Transaction;
import com.yoghurt.crypto.transactions.client.widget.TransactionHexViewer;

public class TransactionBreakdownViewImpl extends Composite implements TransactionBreakdownView {
  interface TransactionBreakdownViewImplUiBinder extends UiBinder<Widget, TransactionBreakdownViewImpl> {}

  private static final TransactionBreakdownViewImplUiBinder UI_BINDER = GWT.create(TransactionBreakdownViewImplUiBinder.class);

  @UiField TransactionHexViewer transactionHexViewer;

  @Inject
  public TransactionBreakdownViewImpl() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setTransactionData(final Transaction transaction) {
    transactionHexViewer.setTransaction(transaction);
  }
}
