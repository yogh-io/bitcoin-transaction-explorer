package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.place.TransactionBreakdownPlace;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.util.transaction.TransactionEncodeUtil;
import com.yoghurt.crypto.transactions.shared.util.transaction.TransactionParseUtil;

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
      TransactionParseUtil.parseTransactionBytes(Hex.decode(place.getHex()), transaction);
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
