package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.client.widget.TransactionPlaceRouter;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;

public interface TransactionView extends IsWidget {

  public interface Presenter extends TransactionPlaceRouter {

  }

  void setTransaction(Transaction transaction);

  void setBlockchainInformation(TransactionInformation transactionInformation);

  void setPresenter(Presenter transactionActivity);
}
