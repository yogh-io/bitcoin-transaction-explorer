package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace.TransactionType;
import com.yoghurt.crypto.transactions.client.util.MorphCallback;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;
import com.yoghurt.crypto.transactions.shared.util.transaction.TransactionEncodeUtil;
import com.yoghurt.crypto.transactions.shared.util.transaction.TransactionParseUtil;

public class TransactionActivity extends LookupActivity<Transaction, TransactionPlace> implements
TransactionView.Presenter {
  private final TransactionView view;
  private final BlockchainRetrievalServiceAsync service;

  @Inject
  public TransactionActivity(final TransactionView view, @Assisted final TransactionPlace place,
      final BlockchainRetrievalServiceAsync service) {
    super(place);
    this.view = view;
    this.service = service;
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final Transaction transaction) {
    panel.setWidget(view);

    // Prepare a raw transaction object that may be encoded in a hex viewer
    // TODO This is ugly, logically, maybe we could have the view do this with the Transaction object, which is really the only object this activity
    // should be concerned about.
    final RawTransactionContainer rawTransaction = new RawTransactionContainer();
    try {
      TransactionEncodeUtil.encodeTransaction(transaction, rawTransaction);
    } catch (final Exception e) {
      GWT.log("Encode error");
      // TODO Parse error, display how far we got
      throw new RuntimeException(e);
    }

    view.setTransactionData(transaction, rawTransaction);
  }

  @Override
  protected boolean mustPerformLookup(final TransactionPlace place) {
    return place.getType() == TransactionType.ID;
  }

  @Override
  protected Transaction createInfo(final TransactionPlace place) {
    return getTransactionFromHex(place.getHex());
  }

  private Transaction getTransactionFromHex(final String hex) {
    return TransactionParseUtil.parseTransactionBytes(Hex.decode(hex));
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