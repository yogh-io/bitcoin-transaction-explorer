package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;
import com.yoghurt.crypto.transactions.shared.util.block.BlockParseUtil;
import com.yoghurt.crypto.transactions.shared.util.transaction.TransactionParseUtil;

public abstract class LookupActivity<E, P extends Place> extends AbstractActivity {
  protected final P place;
  protected final BlockchainRetrievalServiceAsync service;

  public LookupActivity(final P place, final BlockchainRetrievalServiceAsync service) {
    this.place = place;
    this.service = service;
  }

  @Override
  public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
    if(!mustPerformLookup(place)) {
      doDeferredStart(panel, createInfo(place));
    } else {
      doLookup(place, new AsyncCallback<E>() {
        @Override
        public void onSuccess(final E result) {
          doDeferredStart(panel, result);
        }

        @Override
        public void onFailure(final Throwable caught) {
          doDeferredError(panel, caught);
        }
      });
    }
  }

  protected Block getBlockFromHex(final String hex) {
    return getBlockFromHex(new Block(), hex);
  }

  protected Transaction getTransactionFromHex(final String hex) {
    return getTransactionFromHex(new Transaction(), hex);
  }

  protected Block getBlockFromHex(final Block b, final String hex) {
    if (hex == null) {
      return null;
    }

    BlockParseUtil.parseBlockBytes(Hex.decode(hex), b);
    return b;
  }

  protected Transaction getTransactionFromHex(final Transaction t, final String hex) {
    if (hex == null) {
      return null;
    }

    TransactionParseUtil.parseTransactionBytes(Hex.decode(hex), t);
    return t;
  }

  protected abstract void doLookup(final P place, final AsyncCallback<E> callback);

  /**
   * Whether or not to perform an asynchronous lookup.
   * 
   * @param place The place to determine whether or not to lookup for.
   * 
   * @return Whether or not to lookup asynchronously.
   */
  protected abstract boolean mustPerformLookup(final P place);

  /**
   * Create the desired information object out of the place (not doing asynchronous lookup, all information is already available)
   * 
   * @param place Place to get information out of.
   * 
   * @return Information
   */
  protected abstract E createInfo(final P place);

  /**
   * Deferredly start the activity after the information to be given to the concrete implementation has been determined.
   * 
   * @param panel Panel in which content may live.
   * @param info Information for which content will be created.
   */
  protected abstract void doDeferredStart(final AcceptsOneWidget panel, final E info);

  /**
   * Defferedly error out because something went wrong.
   * 
   * @param panel Panel in which content may live.
   * @param caught All information we're allowed to have about what may have gone wrong.
   */
  protected abstract void doDeferredError(AcceptsOneWidget panel, Throwable caught);
}
