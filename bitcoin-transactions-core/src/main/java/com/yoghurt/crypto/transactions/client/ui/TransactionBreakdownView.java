package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.client.domain.Transaction;

public interface TransactionBreakdownView extends IsWidget {

  public interface Presenter {

  }

  void setTransactionData(Transaction transaction);

}
