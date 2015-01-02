package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.place.MinePlace;
import com.yoghurt.crypto.transactions.client.place.MinePlace.MineDataType;
import com.yoghurt.crypto.transactions.client.util.AppAsyncCallback;
import com.yoghurt.crypto.transactions.client.util.MorphCallback;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;
import com.yoghurt.crypto.transactions.shared.util.block.BlockEncodeUtil;
import com.yoghurt.crypto.transactions.shared.util.block.BlockParseUtil;
import com.yoghurt.crypto.transactions.shared.util.transaction.ComputeUtil;

public class MineActivity extends LookupActivity<Block, MinePlace> implements MineView.Presenter {
  private static final long NONCE_DECREMENT = 10;

  /**
   * Thirty seconds
   */
  private static final int LATEST_BLOCK_POLL_DELAY = 10 * 1000;

  private static final int SHORT_INITIAL_POLL_DELAY = 500;

  private final MineView view;
  private final BlockchainRetrievalServiceAsync service;

  private final Timer timer = new Timer() {
    @Override
    public void run() {
      getLatestBlock();
    }
  };

  private String latestBlock;

  private final AsyncCallback<String> callback = new AppAsyncCallback<String>() {
    @Override
    public void onSuccess(final String result) {
      retrieving = false;

      if(result == null) {
        timer.schedule(LATEST_BLOCK_POLL_DELAY);
        return;
      }

      // I have no idea which of the endians this is and this isn't a time in which I like being in a thinking mood
      final byte[] otherEndianBytes = Hex.decode(result);
      ArrayUtil.reverse(otherEndianBytes);
      final String otherEndianResult = Str.toString(Hex.encode(otherEndianBytes));

      if(!otherEndianResult.equals(latestBlock)) {
        latestBlock = result;
        view.broadcastLatestBlock(otherEndianResult);
      }

      timer.schedule(LATEST_BLOCK_POLL_DELAY);
    }
  };

  private boolean retrieving;

  @Inject
  public MineActivity(final MineView view, @Assisted final MinePlace place, final BlockchainRetrievalServiceAsync service) {
    super(place);

    this.view = view;
    this.service = service;
  }

  @Override
  protected void doLookup(final MinePlace place, final AsyncCallback<Block> callback) {
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
    case LAST:
      service.getLastRawBlockHex(morphCallback);
      break;
    case ID:
      service.getRawBlockHex(place.getPayload(), morphCallback);
      break;
    default:
      callback.onFailure(new IllegalStateException("No support lookup for type: " + place.getType().name()));
      return;
    }
  }

  @Override
  public void onStop() {
    timer.cancel();
    view.cancel();

    super.onStop();
  }

  @Override
  protected boolean mustPerformLookup(final MinePlace place) {
    return place.getType() == MineDataType.HEIGHT
        || place.getType() == MineDataType.LAST
        || place.getType() == MineDataType.ID;
  }

  @Override
  protected Block createInfo(final MinePlace place) {
    return place.getType() == MineDataType.RAW ? getBlockFromHex(place.getPayload()) : null;
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final Block block) {
    view.setPresenter(this);
    panel.setWidget(view);

    latestBlock = Str.toString(Hex.encode(block.getBlockHash()));

    final RawBlockContainer rawBlock = new RawBlockContainer();
    try {
      BlockEncodeUtil.encodeBlock(block, rawBlock);
    } catch (final Throwable e) {
      // Eat.
    }

    block.setNonce(block.getNonce() - NONCE_DECREMENT);
    final byte[] blockHash = ComputeUtil.computeDoubleSHA256(rawBlock.values());
    block.setBlockHash(blockHash);

    final boolean keepUpWithTip = place.getType() == MineDataType.LAST;

    view.setInformation(block, rawBlock, keepUpWithTip);
  }

  @Override
  public void startPoll() {
    timer.schedule(SHORT_INITIAL_POLL_DELAY);
  }

  @Override
  public void pausePoll() {
    retrieving = false;
    timer.cancel();
  }

  private Block getBlockFromHex(final String hex) {
    final Block b = new Block();

    try {
      BlockParseUtil.parseBlockBytes(Hex.decode(hex), b);
    } catch (final IllegalStateException e) {
      e.printStackTrace();
      // Eat
    }

    return b;
  }

  @Override
  public void getLatestBlock() {
    if(retrieving) {
      return;
    }

    retrieving = true;
    service.getLatestBlockHash(callback);
  }
}
