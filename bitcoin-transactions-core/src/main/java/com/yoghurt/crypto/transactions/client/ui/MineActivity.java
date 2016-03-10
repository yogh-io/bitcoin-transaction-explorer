package com.yoghurt.crypto.transactions.client.ui;

import java.util.ArrayList;

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
import com.yoghurt.crypto.transactions.client.util.block.BlockEncodeUtil;
import com.yoghurt.crypto.transactions.client.util.block.BlockParseUtil;
import com.yoghurt.crypto.transactions.client.util.transaction.ComputeUtil;
import com.yoghurt.crypto.transactions.client.util.transaction.TransactionEncodeUtil;
import com.yoghurt.crypto.transactions.client.util.transaction.TransactionParseUtil;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;
import com.yoghurt.crypto.transactions.shared.util.NumberEncodeUtil;

/**
 * TODO GET RID OF HISTORICAL SIMULATION AND USE GETWORK/GETBLOCKTEMPLATE TYPE OF CONSTRUCT
 */
public class MineActivity extends LookupActivity<BlockInformation, MinePlace> implements MineView.Presenter {
  private static final String SEAN_OUTPOST_HASH_160 = "01000000010000000000000000000000000000000000000000000000000000000000000000FFFFFFFF0487654321FFFFFFFF0100F90295000000001976A914DC863734A218BFE83EF770EE9D41A27F824A6E5688AC00000000";

  /**
   * Thirty seconds
   */
  private static final int LATEST_BLOCK_POLL_DELAY = 30 * 1000;
  private static final int SHORT_INITIAL_POLL_DELAY = 500;

  private final MineView view;

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

      if (result == null) {
        timer.schedule(LATEST_BLOCK_POLL_DELAY);
        return;
      }

      // I have no idea which of the endians this is and this isn't a time in which I like being in a thinking mood
      final byte[] otherEndianBytes = Hex.decode(result);
      ArrayUtil.reverse(otherEndianBytes);
      final String otherEndianResult = Str.toString(Hex.encode(otherEndianBytes));

      if (!otherEndianResult.equals(latestBlock)) {
        latestBlock = result;
        view.broadcastLatestBlock(otherEndianResult);
      }

      timer.schedule(LATEST_BLOCK_POLL_DELAY);
    }
  };

  private boolean retrieving;

  @Inject
  public MineActivity(final MineView view, @Assisted final MinePlace place, final BlockchainRetrievalServiceAsync service) {
    super(place, service);
    this.view = view;
  }

  @Override
  protected void doLookup(final MinePlace place, final AsyncCallback<BlockInformation> callback) {
    switch (place.getType()) {
    case HEIGHT:
      service.getBlockInformationFromHeight(Integer.parseInt(place.getPayload()), callback);
      break;
    case ID:
      service.getBlockInformationFromHash(place.getPayload(), callback);
      break;
    case LAST:
      service.getBlockInformationLast(new MorphCallback<BlockInformation, BlockInformation>(callback) {
        @Override
        protected BlockInformation morphResult(final BlockInformation result) {
          final TransactionInformation ti = new TransactionInformation();
          ti.setRawHex(SEAN_OUTPOST_HASH_160);
          result.setCoinbaseInformation(ti);
          return result;
        }
      });
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
    return place.getType() == MineDataType.HEIGHT || place.getType() == MineDataType.LAST || place.getType() == MineDataType.ID;
  }

  @Override
  protected BlockInformation createInfo(final MinePlace place) {
    final BlockInformation blockInformation = new BlockInformation();
    blockInformation.setRawBlockHeaders(place.getPayload());

    return blockInformation;
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final BlockInformation blockInformation) {
    view.setPresenter(this);
    panel.setWidget(view);

    Block block;
    final Transaction coinbase;
    try {
      block = BlockParseUtil.parseBlockBytes(Hex.decode(blockInformation.getRawBlockHeaders()));
      coinbase = TransactionParseUtil.parseTransactionBytes(Hex.decode(blockInformation.getCoinbaseInformation().getRawHex()));
    } catch (final Throwable e) {
      // TODO Throw error
      return;
    }

    // Store this block's hash
    latestBlock = Str.toString(Hex.encode(block.getBlockHash()));

    final RawBlockContainer rawBlock = new RawBlockContainer();
    final RawTransactionContainer rawTransaction = new RawTransactionContainer();
    try {
      BlockEncodeUtil.encodeBlock(block, rawBlock);
      TransactionEncodeUtil.encodeTransaction(coinbase, rawTransaction);
    } catch (final Throwable e) {
      // TODO Throw error
      return;
    }

    final boolean customMiningSession = isCustomMiningPlace();

    // If this is a custom mining session (which includes custom coinbase), do some special stuff (mining on top of tip)
    if(customMiningSession) {
      // Set the previous block hash to last/current block hash
      final byte[] blockHash = ComputeUtil.computeDoubleSHA256(rawBlock.values());
      block.setPreviousBlockHash(blockHash);
      rawBlock.setPreviousBlockHash(blockHash);

      // Reset the nonce
      block.setNonce(0);
      rawBlock.setNonce(NumberEncodeUtil.encodeUint32(0));

      // Set the merkle root to the coinbase tx only
      final ArrayList<byte[]> txs = new ArrayList<byte[]>();
      txs.add(rawTransaction.getBytes());
      final byte[] merkleRoot = ComputeUtil.computeMerkleRoot(txs);
      block.setMerkleRoot(merkleRoot);
      rawBlock.setMerkleRoot(merkleRoot);
    }

    view.setInformation(block, rawBlock, rawTransaction, customMiningSession);
  }

  private boolean isCustomMiningPlace() {
    return place.getType() == MineDataType.LAST || place.getType() == MineDataType.RAW;
  }

  @Override
  protected void doDeferredError(final AcceptsOneWidget panel, final Throwable caught) {
    // Not supported
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

  @Override
  public void getLatestBlock() {
    if (retrieving) {
      return;
    }

    retrieving = true;
    service.getLatestBlockHash(callback);
  }
}
