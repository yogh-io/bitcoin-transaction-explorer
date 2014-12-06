package com.yoghurt.crypto.transactions.shared.domain;

import java.util.LinkedHashMap;

import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;

public class RawBlockContainer extends LinkedHashMap<BlockPartType, byte[]> {
  private static final long serialVersionUID = -8651827947470285501L;

  public byte[] getVersion() {
    return get(BlockPartType.VERSION);
  }

  public void setVersion(final byte[] version) {
    put(BlockPartType.VERSION, version);
  }

  public byte[] getPreviousBlockHash() {
    return get(BlockPartType.PREV_BLOCK_HASH);
  }

  public void setPreviousBlockHash(final byte[] previousBlockHash) {
    put(BlockPartType.PREV_BLOCK_HASH, previousBlockHash);
  }

  public byte[] getMerkleRoot() {
    return get(BlockPartType.MERKLE_ROOT);
  }

  public void setMerkleRoot(final byte[] merkleRoot) {
    put(BlockPartType.MERKLE_ROOT, merkleRoot);
  }

  public byte[] getTimestamp() {
    return get(BlockPartType.TIMESTAMP);
  }

  public void setTimestamp(final byte[] timestamp) {
    put(BlockPartType.TIMESTAMP, timestamp);
  }

  public byte[] getBits() {
    return get(BlockPartType.BITS);
  }

  public void setBits(final byte[] bits) {
    put(BlockPartType.BITS, bits);
  }

  public byte[] getNonce() {
    return get(BlockPartType.NONCE);
  }

  public void setNonce(final byte[] nonce) {
    put(BlockPartType.NONCE, nonce);
  }

  /**
   * Hate doing this.. FIXME Refactor out of here
   */
  public RawBlockContainer copy() {
    final RawBlockContainer container = new RawBlockContainer();
    container.setVersion(ArrayUtil.arrayCopy(getVersion()));
    container.setPreviousBlockHash(ArrayUtil.arrayCopy(getPreviousBlockHash()));
    container.setMerkleRoot(ArrayUtil.arrayCopy(getMerkleRoot()));
    container.setTimestamp(ArrayUtil.arrayCopy(getTimestamp()));
    container.setBits(ArrayUtil.arrayCopy(getBits()));
    container.setNonce(ArrayUtil.arrayCopy(getNonce()));
    return container;
  }
}
