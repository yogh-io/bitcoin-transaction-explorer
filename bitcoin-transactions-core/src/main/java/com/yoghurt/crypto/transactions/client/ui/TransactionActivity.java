package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace.TransactionDataType;
import com.yoghurt.crypto.transactions.client.util.AppAsyncCallback;
import com.yoghurt.crypto.transactions.client.util.MorphCallback;
import com.yoghurt.crypto.transactions.client.util.ParseUtil;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;

public class TransactionActivity extends LookupActivity<Transaction, TransactionPlace> implements TransactionView.Presenter {
  private final TransactionView view;

  private boolean transactionHasError;

  @Inject
  public TransactionActivity(final TransactionView view, @Assisted final TransactionPlace place, final BlockchainRetrievalServiceAsync service) {
    super(place, service);
    this.view = view;
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final Transaction transaction) {
    panel.setWidget(view);

    view.setTransaction(transaction, transactionHasError);

    // If an error occurred while parsing, don't bother getting the tx info
    if(transactionHasError) {
      return;
    }

    service.getTransactionInformation(Str.toString(Hex.encode(transaction.getTransactionId())), new AppAsyncCallback<TransactionInformation>() {
      @Override
      public void onSuccess(final TransactionInformation result) {
        view.setBlockchainInformation(result);
      }
    });
  }

  @Override
  protected void doDeferredError(final AcceptsOneWidget panel, final Throwable caught) {
    panel.setWidget(view);

    view.setError(place.getHex(), caught);
  }

  @Override
  protected boolean mustPerformLookup(final TransactionPlace place) {
    return place.getType() == TransactionDataType.ID;
  }

  @Override
  protected Transaction createInfo(final TransactionPlace place) {
    return ParseUtil.getTransactionFromHex(place.getHex());
  }

  @Override
  protected void doLookup(final TransactionPlace place, final AsyncCallback<Transaction> callback) {
    service.getRawTransactionHex(place.getHex(), new MorphCallback<String, Transaction>(callback) {
      @Override
      protected Transaction morphResult(final String result) {
        return ParseUtil.getTransactionFromHex(result);
      }
    });
  }
}