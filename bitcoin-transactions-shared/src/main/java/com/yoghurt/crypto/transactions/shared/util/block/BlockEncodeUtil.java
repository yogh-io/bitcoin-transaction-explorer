package com.yoghurt.crypto.transactions.shared.util.block;

import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.BlockPartType;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockPart;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;
import com.yoghurt.crypto.transactions.shared.util.NumberEncodeUtil;

public final class BlockEncodeUtil {
  private BlockEncodeUtil() {}

  public static RawBlockContainer encodeBlock(final Block block) {
    return encodeBlock(block, new RawBlockContainer());
  }

  public static RawBlockContainer encodeBlock(final Block block, final RawBlockContainer container) {
    // Encode the version
    container.add(encodeVersion(block));

    // Encode previous transaction hash
    container.add(encodePreviousBlockHash(block));

    // Encode merkle root
    container.add(encodeMerkleRoot(block));

    // Encode timestamp
    container.add(encodeTimestamp(block));

    // Encode bits
    container.add(encodeBits(block));

    // Encode nonce
    container.add(encodeNonce(block));

    return container;
  }

  private static RawBlockPart encodeVersion(final Block block) {
    return new RawBlockPart(BlockPartType.VERSION, NumberEncodeUtil.encodeUint32(block.getVersion()));
  }

  private static RawBlockPart encodePreviousBlockHash(final Block block) {
    final byte[] previousBlockHashBytes = block.getPreviousBlockHash();
    ArrayUtil.reverse(previousBlockHashBytes);

    return new RawBlockPart(BlockPartType.PREV_BLOCK_HASH, previousBlockHashBytes);
  }

  private static RawBlockPart encodeMerkleRoot(final Block block) {
    final byte[] merkleRootBytes = block.getMerkleRoot();
    ArrayUtil.reverse(merkleRootBytes);

    return new RawBlockPart(BlockPartType.MERKLE_ROOT, merkleRootBytes);
  }

  private static RawBlockPart encodeTimestamp(final Block block) {
    return new RawBlockPart(BlockPartType.TIMESTAMP, NumberEncodeUtil.encodeUint32(block.getTimestamp().getTime() / 1000));
  }

  private static RawBlockPart encodeBits(final Block block) {
    final byte[] bitsBytes = block.getBits();
    ArrayUtil.reverse(bitsBytes);

    return new RawBlockPart(BlockPartType.BITS, bitsBytes);
  }

  private static RawBlockPart encodeNonce(final Block block) {
    return new RawBlockPart(BlockPartType.NONCE, NumberEncodeUtil.encodeUint32(block.getNonce()));
  }
}
