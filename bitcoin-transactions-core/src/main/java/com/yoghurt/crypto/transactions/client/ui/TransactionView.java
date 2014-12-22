package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;

public interface TransactionView extends IsWidget {

  public interface Presenter {

  }

  void setBlockchainInformation(TransactionInformation transactionInformation);

  void setTransaction(Transaction transaction, boolean transactionHasError);
}
