package com.yoghurt.crypto.transactions.client.util.address;

import com.googlecode.gwt.crypto.bouncycastle.util.Arrays;
import com.yoghurt.crypto.transactions.client.util.transaction.ComputeUtil;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;

public class Base58CheckUtil {
  private static final int CHECKSUM_LENGTH = 4;

  public static Base58CheckContents parseBase58Check(final String base58Check) {
    final byte[] decode = Base58.decode(base58Check);

    final Base58CheckContents contents = new Base58CheckContents();

    contents.setVersion(decode[0]);
    contents.setChecksum(extractChecksum(decode));
    contents.setPayload(ArrayUtil.arrayCopy(decode, 1, decode.length - CHECKSUM_LENGTH));

    return contents;
  }

  public static byte[] computeChecksum(final byte version, final byte[] payload) {
    final byte[] checksum = ComputeUtil.computeDoubleSHA256(new byte[] { version }, payload);

    return ArrayUtil.arrayCopy(checksum, 0, CHECKSUM_LENGTH);
  }

  public static boolean isValid(final Base58CheckContents base58contents) {
    return Arrays.areEqual(base58contents.getChecksum(), computeChecksum(base58contents.getVersion(), base58contents.getPayload()));
  }

  /**
   * Extract the checksum from some thing that is a base58check format
   * @param thing thing checksum is extracted form
   * @return last 4 bytes of thing
   */
  private static byte[] extractChecksum(final byte[] thing) {
    return ArrayUtil.arrayCopy(thing, thing.length - CHECKSUM_LENGTH, thing.length);
  }
}
