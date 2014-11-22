package com.yoghurt.crypto.transactions.shared.util.transaction;

import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;


public class ComputeUtil {

  /**
   * Compute the double SHA256 digest out of a set of bytes, returns the bytes in LE
   * 
   * FIXME Using external library imported into this project because there's no maven support for it
   * May want to move to a library that does exist on maven central, does not use native JS (we'll want to unit test this),
   * and supports SHA256 (gwt-crypto does not, which we're using for Hex decoding, also supports most crypto _except_ SHA-256)
   * 
   * @param txBytes Bytes to digest
   * 
   * @return Doubly hashed input bytes in LE
   */
  public static byte[] computeDoubleSHA256(final byte[] txBytes) {
    final SHA256 round1 = new SHA256();
    round1.feed(txBytes);

    final SHA256 round2 = new SHA256();
    round2.feed(round1.finish());

    final byte[] txHash = round2.finish();

    ArrayUtil.reverse(txHash);

    return txHash;
  }
}
