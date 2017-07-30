package com.yoghurt.crypto.transactions.client.util.transaction;

import java.util.ArrayList;
import java.util.Collection;

import com.yoghurt.crypto.transactions.client.util.crypto.RIPEMD160;
import com.yoghurt.crypto.transactions.client.util.crypto.SHA256;

/**
 * FIXME Using external library imported into this project because there's no
 * maven support for it May want to move to a library that does exist on maven
 * central, does not use native JS (we'll want to unit test this), and supports
 * SHA256 (gwt-crypto does not, which we're using for Hex decoding, also
 * supports most crypto _except_ SHA-256)
 */
public class ComputeUtil {

  /**
   * Compute the double SHA256 digest out of a collection of bytes
   *
   * @param bytes
   *          Bytes to digest
   *
   * @return Doubly hashed input bytes
   */
  public static byte[] computeDoubleSHA256(final byte[] bytes) {
    return computeSHA256(computeSHA256(bytes));
  }

  /**
   * Compute the double SHA256 digest out of a collection (of a collection) of
   * bytes
   *
   * @param bytes
   *          Collection of bytes to digest
   *
   * @return Doubly hashed input bytes
   */
  public static byte[] computeDoubleSHA256(final Collection<byte[]> bytes) {
    return computeSHA256(computeSHA256(bytes));
  }

  public static byte[] computeDoubleSHA256(final byte[]... bytes) {
    return computeSHA256(computeSHA256(bytes));
  }

  public static byte[] computeSHA256(final byte[] bytes) {
    final SHA256 digest = new SHA256();
    digest.feed(bytes);

    return digest.finish();
  }

  public static byte[] computeSHA256(final Collection<byte[]> bytesSet) {
    final SHA256 digest = new SHA256();

    for (final byte[] bytes : bytesSet) {
      digest.feed(bytes);
    }

    return digest.finish();
  }

  public static byte[] computeSHA256(final byte[]... bytesSet) {
    final SHA256 digest = new SHA256();

    for (final byte[] bytes : bytesSet) {
      digest.feed(bytes);
    }

    return digest.finish();
  }

  @Deprecated
  public static byte[] computeMerkleRoot(final byte[] tx) {
    return computeDoubleSHA256(tx);
  }

  /**
   * TODO Test and support >1 tx
   */
  @Deprecated
  public static byte[] computeMerkleRoot(final ArrayList<byte[]> txs) {
    if (txs.size() > 1) {
      throw new IllegalStateException(">1 tx merkleroot not implemented.");
    }

    final byte[] bs = txs.get(0);

    return computeDoubleSHA256(bs);
  }

  public static byte[] computeHash160(final byte[] bytes) {
    final RIPEMD160 digest = new RIPEMD160();

    return digest.digest(computeSHA256(bytes));
  }

}
