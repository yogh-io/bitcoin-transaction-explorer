package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.place.BlockPlace;
import com.yoghurt.crypto.transactions.client.place.BlockPlace.BlockDataType;
import com.yoghurt.crypto.transactions.client.util.MorphCallback;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;
import com.yoghurt.crypto.transactions.shared.util.block.BlockParseUtil;
import com.yoghurt.crypto.transactions.shared.util.transaction.TransactionParseUtil;

public class BlockActivity extends LookupActivity<Block, BlockPlace> implements BlockView.Presenter {
  private final BlockView view;
  private final BlockchainRetrievalServiceAsync service;

  @Inject
  public BlockActivity(final BlockView view, @Assisted final BlockPlace place, final BlockchainRetrievalServiceAsync service) {
    super(place);
    this.view = view;
    this.service = service;
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final Block block) {
    panel.setWidget(view);

    view.setBlock(block);

    if(block == null) {
      return;
    }

    service.getBlockInformation(Str.toString(Hex.encode(block.getBlockHash())), new AsyncCallback<BlockInformation>() {
      @Override
      public void onSuccess(final BlockInformation result) {
        final Transaction tx = getTransactionFromHex(result.getRawCoinbaseTransaction());

        view.setBlockInformation(result, tx);
      }

      @Override
      public void onFailure(final Throwable caught) {
        view.setBlockInformation(null, null);
      }
    });
  }

  @Override
  protected boolean mustPerformLookup(final BlockPlace place) {
    return place.getType() == BlockDataType.ID || place.getType() == BlockDataType.HEIGHT || place.getType() == BlockDataType.LAST;
  }

  @Override
  protected Block createInfo(final BlockPlace place) {
    return place.getType() == BlockDataType.RAW ? getBlockFromHex(place.getPayload()) : null;
  }

  private Block getBlockFromHex(final String hex) {
    if (hex == null) {
      return null;
    }

    final Block b = new Block();

    try {
      BlockParseUtil.parseBlockBytes(Hex.decode(hex), b);
    } catch (final IllegalStateException e) {
      e.printStackTrace();
      // Eat
    }

    return b;
  }

  private Transaction getTransactionFromHex(final String hex) {
    if (hex == null) {
      return null;
    }

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
  protected void doLookup(final BlockPlace place, final AsyncCallback<Block> callback) {
    final MorphCallback<String, Block> morphCallback = new MorphCallback<String, Block>(callback) {
      @Override
      protected Block morphResult(final String result) {
        return getBlockFromHex(result);
      }
    };

    switch (place.getType()) {
    case HEIGHT:
      service.getRawBlockHex(Integer.parseInt(place.getPayload()), morphCallback);
      break;
    case ID:
      service.getRawBlockHex(place.getPayload(), morphCallback);
      break;
    case LAST:
      service.getLastRawBlockHex(morphCallback);
      break;
    default:
      callback.onFailure(new IllegalStateException("No support lookup for type: " + place.getType().name()));
      return;
    }
  }

  @Override
  protected void doDeferredError(final AcceptsOneWidget panel, final Throwable caught) {
    // Not supported
  }
}