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
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;
import com.yoghurt.crypto.transactions.shared.util.transaction.TransactionParseUtil;

public class TransactionActivity extends LookupActivity<Transaction, TransactionPlace> implements TransactionView.Presenter {
  private final TransactionView view;
  private final BlockchainRetrievalServiceAsync service;

  @Inject
  public TransactionActivity(final TransactionView view, @Assisted final TransactionPlace place, final BlockchainRetrievalServiceAsync service) {
    super(place);
    this.view = view;
    this.service = service;
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final Transaction transaction) {
    panel.setWidget(view);

    view.setTransaction(transaction);

    service.getTransactionInformation(Str.toString(Hex.encode(transaction.getTransactionId())), new AppAsyncCallback<TransactionInformation>() {
      @Override
      public void onSuccess(final TransactionInformation result) {
        view.setBlockchainInformation(result);
      }
    });
  }

  @Override
  protected boolean mustPerformLookup(final TransactionPlace place) {
    return place.getType() == TransactionDataType.ID;
  }

  @Override
  protected Transaction createInfo(final TransactionPlace place) {
    return getTransactionFromHex(place.getHex());
  }

  private Transaction getTransactionFromHex(final String hex) {
    final Transaction t = new Transaction();

    try {
      TransactionParseUtil.parseTransactionBytes(Hex.decode(hex), t);
    } catch (final IllegalStateException e) {
      e.printStackTrace();
      // Eat
    }

    return t;
  }

  @Override
  protected void doLookup(final TransactionPlace place, final AsyncCallback<Transaction> callback) {
    service.getRawTransactionHex(place.getHex(), new MorphCallback<String, Transaction>(callback) {
      @Override
      protected Transaction morphResult(final String result) {
        return getTransactionFromHex(result);
      }
    });
  }
}