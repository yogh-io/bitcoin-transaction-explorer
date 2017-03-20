package com.yoghurt.crypto.transactions.client.ui;

import com.yoghurt.crypto.transactions.client.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.service.domain.TransactionInformation;

public interface TransactionView extends LazyProgressWidget {

  public interface Presenter {

  }

  void setTransactionInformation(TransactionInformation transactionInformation);

  void setTransaction(Transaction transaction, boolean transactionHasError);

  void setError(String hash, Throwable caught);
}
