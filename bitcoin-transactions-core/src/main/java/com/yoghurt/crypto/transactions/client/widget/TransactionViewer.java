package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.yoghurt.crypto.transactions.client.place.TransactionPlaceRouter;
import com.yoghurt.crypto.transactions.client.resources.R;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;

public class TransactionViewer extends ValueViewer {
  private final ClickHandler mouseClickHandler = new ClickHandler() {
    @Override
    public void onClick(final ClickEvent event) {
      presenter.goToTransaction(value);
    }
  };
  private final TransactionPlaceRouter presenter;

  /**
   * Flag indicating whether or not the value is LE or BE, if it's BE, will convert to LE
   */
  private final boolean reverse;

  public TransactionViewer(final TransactionPlaceRouter presenter, final boolean coinbase) {
    this(presenter, true, coinbase);
  }

  public TransactionViewer(final TransactionPlaceRouter presenter, final boolean reverse, final boolean coinbase) {
    super(R.color().transactionHash(), new SimpleContextFactory<String>() {
      @Override
      protected String getTextValue(final String value) {
        if(coinbase) {
          return "This is a coinbase transaction, it has no inputs.";
        } else {
          return "View this transaction.";
        }
      }
    });

    this.presenter = presenter;
    this.reverse = reverse;

    if(!coinbase) {
      setMouseClickHandler(mouseClickHandler);
    }
  }

  @Override
  public void setValue(final byte[] hash) {
    if (reverse) {
      final byte[] hashCopy = ArrayUtil.arrayCopy(hash);
      ArrayUtil.reverse(hashCopy);

      super.setValue(hashCopy);
    } else {
      super.setValue(hash);
    }
  }
}
