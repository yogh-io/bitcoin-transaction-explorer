package com.yoghurt.crypto.transactions.client.ui;

import java.util.ArrayList;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.place.MinePlace;
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

/**
 * TODO GET RID OF HISTORICAL SIMULATION AND USE GETWORK/GETBLOCKTEMPLATE TYPE OF CONSTRUCT
 */
public class MineActivity extends AbstractActivity implements MineView.Presenter {
  private static final String GENESIS_COINBASE = "01000000010000000000000000000000000000000000000000000000000000000000000000FFFFFFFF4D04FFFF001D0104455468652054696D65732030332F4A616E2F32303039204368616E63656C6C6F72206F6E206272696E6B206F66207365636F6E64206261696C6F757420666F722062616E6B73FFFFFFFF0100F2052A01000000434104678AFDB0FE5548271967F1A67130B7105CD6A828E03909A67962E0EA1F61DEB649F6BC3F4CEF38C4F35504E51EC112DE5C384DF7BA0B8D578A4C702B6BF11D5FAC00000000";

  private final MineView view;

  private final MinePlace place;

  public MineActivity(final MineView view, final MinePlace place) {
    this.view = view;
    this.place = place;
  }

  @Override
  public void onStop() {
    view.cancel();

    super.onStop();
  }

  @Override
  public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
    final BlockInformation blockInformation = new BlockInformation();
    blockInformation.setRawBlockHeaders(place.getPayload());
    blockInformation.setRawCoinbaseTransaction(GENESIS_COINBASE);

    panel.setWidget(view);

    Block block;
    final Transaction coinbase;
    try {
      block = BlockParseUtil.parseBlockBytes(Hex.decode(blockInformation.getRawBlockHeaders()));
      coinbase = TransactionParseUtil.parseTransactionBytes(Hex.decode(blockInformation.getRawCoinbaseTransaction()));
    } catch (final Throwable e) {
      GWT.log(e.getMessage());
      return;
    }

    final RawBlockContainer rawBlock = new RawBlockContainer();
    final RawTransactionContainer rawTransaction = new RawTransactionContainer();
    try {
      BlockEncodeUtil.encodeBlock(block, rawBlock);
      TransactionEncodeUtil.encodeTransaction(coinbase, rawTransaction);
    } catch (final Throwable e) {
      GWT.log(e.getMessage());
      return;
    }

    // Set the merkle root to the coinbase tx only
    final ArrayList<byte[]> txs = new ArrayList<byte[]>();
    txs.add(rawTransaction.getBytes());
    final byte[] merkleRoot = ComputeUtil.computeMerkleRoot(txs);
    block.setMerkleRoot(merkleRoot);
    rawBlock.setMerkleRoot(merkleRoot);

    view.setInformation(block, rawBlock, rawTransaction);
  }
}
