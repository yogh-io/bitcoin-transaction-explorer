package com.yoghurt.crypto.transactions.client.util.block;

import java.util.Date;

import com.yoghurt.crypto.transactions.client.domain.Block;
import com.yoghurt.crypto.transactions.client.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.client.util.ArrayUtil;
import com.yoghurt.crypto.transactions.client.util.NumberEncodeUtil;

public final class BlockEncodeUtil {
  private BlockEncodeUtil() {}

  public static RawBlockContainer encodeBlock(final Block block) {
    return encodeBlock(block, new RawBlockContainer());
  }

  public static RawBlockContainer encodeBlock(final Block block, final RawBlockContainer container) {
    // Encode the version
    container.setVersion(encodeVersion(block));

    // Encode previous transaction hash
    container.setPreviousBlockHash(encodePreviousBlockHash(block));

    // Encode merkle root
    container.setMerkleRoot(encodeMerkleRoot(block));

    // Encode timestamp
    container.setTimestamp(encodeTimestamp(block));

    // Encode bits
    container.setBits(encodeBits(block));

    // Encode nonce
    container.setNonce(encodeNonce(block));

    return container;
  }

  public static byte[] encodeVersion(final Block block) {
    return NumberEncodeUtil.encodeUint32(block.getVersion());
  }

  private static byte[] encodePreviousBlockHash(final Block block) {
    return encodePreviousBlockHash(ArrayUtil.arrayCopy(block.getPreviousBlockHash()));
  }

  public static byte[] encodePreviousBlockHash(final byte[] bytes) {
    ArrayUtil.reverse(bytes);
    return bytes;
  }

  private static byte[] encodeMerkleRoot(final Block block) {
    return encodeMerkleRoot(ArrayUtil.arrayCopy(block.getMerkleRoot()));
  }

  public static byte[] encodeMerkleRoot(final byte[] merkleRootBE) {
    ArrayUtil.reverse(merkleRootBE);

    return merkleRootBE;
  }

  private static byte[] encodeTimestamp(final Block block) {
    return encodeTimestamp(block.getTimestamp());
  }

  public static byte[] encodeTimestamp(final Date date) {
    return NumberEncodeUtil.encodeUint32(date.getTime() / 1000);
  }

  private static byte[] encodeBits(final Block block) {
    final byte[] bitsBytes = ArrayUtil.arrayCopy(block.getBits());
    ArrayUtil.reverse(bitsBytes);

    return bitsBytes;
  }

  private static byte[] encodeNonce(final Block block) {
    return encodeNonce(block.getNonce());
  }

  public static byte[] encodeNonce(final long nonce) {
    return NumberEncodeUtil.encodeUint32(nonce);
  }
}
