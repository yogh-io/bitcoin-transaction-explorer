package com.yoghurt.crypto.transactions.client.domain;

import java.io.Serializable;
import java.util.Date;

public class Block implements Serializable {
  private static final long serialVersionUID = -2309928857207399375L;

  private byte[] blockHash;

  private long version;
  private byte[] previousBlockHash;
  private byte[] merkleRoot;
  private Date timestamp;
  private byte[] bits;
  private long nonce;

  public Block() {}

  public byte[] getBlockHash() {
    return blockHash;
  }

  public void setBlockHash(final byte[] hash) {
    this.blockHash = hash;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(final long version) {
    this.version = version;
  }

  public byte[] getPreviousBlockHash() {
    return previousBlockHash;
  }

  public void setPreviousBlockHash(final byte[] previousBlockHash) {
    this.previousBlockHash = previousBlockHash;
  }

  public byte[] getMerkleRoot() {
    return merkleRoot;
  }

  public void setMerkleRoot(final byte[] merkleRoot) {
    this.merkleRoot = merkleRoot;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(final Date timestamp) {
    this.timestamp = timestamp;
  }

  public byte[] getBits() {
    return bits;
  }

  public void setBits(final byte[] bits) {
    this.bits = bits;
  }

  public long getNonce() {
    return nonce;
  }

  public void setNonce(final long nonce) {
    this.nonce = nonce;
  }
}
