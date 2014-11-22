package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;

public interface TransactionView extends IsWidget {

  public interface Presenter {

  }

  void setTransactionData(Transaction transaction, RawTransactionContainer rawTransaction);

}
