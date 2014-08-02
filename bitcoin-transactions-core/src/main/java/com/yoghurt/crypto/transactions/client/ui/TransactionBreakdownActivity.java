package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.yoghurt.crypto.transactions.client.domain.transaction.RawTransactionContainer;
import com.yoghurt.crypto.transactions.client.domain.transaction.Transaction;
import com.yoghurt.crypto.transactions.client.place.TransactionBreakdownPlace;
import com.yoghurt.crypto.transactions.client.util.TransactionEncodeUtil;
import com.yoghurt.crypto.transactions.client.util.TransactionParseUtil;

public class TransactionBreakdownActivity extends AbstractActivity implements TransactionBreakdownView.Presenter {
  private final TransactionBreakdownView view;
  private final TransactionBreakdownPlace place;

  @Inject
  public TransactionBreakdownActivity(final TransactionBreakdownView view, @Assisted final TransactionBreakdownPlace place) {
    this.view = view;
    this.place = place;
  }

  @Override
  public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
    panel.setWidget(view);

    final Transaction transaction = new Transaction();
    final RawTransactionContainer rawTransaction = new RawTransactionContainer();

    try {
      TransactionParseUtil.parseTransactionHex(place.getHex(), transaction);
    } catch (final Exception e) {
      GWT.log("Parse error");
      // TODO Parse error, display how far we got
      throw new RuntimeException(e);
    }
    try {
      TransactionEncodeUtil.encodeTransaction(transaction, rawTransaction);
    } catch (final Exception e) {
      GWT.log("Encode error");
      // TODO Parse error, display how far we got
      throw new RuntimeException(e);
    }


    view.setTransactionData(transaction, rawTransaction);
  }
}
