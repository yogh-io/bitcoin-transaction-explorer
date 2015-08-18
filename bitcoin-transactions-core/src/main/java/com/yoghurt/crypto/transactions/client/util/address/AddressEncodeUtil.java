package com.yoghurt.crypto.transactions.client.util.address;

import com.yoghurt.crypto.transactions.shared.domain.Address;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;

public class AddressEncodeUtil {

  public static byte[] encodeAddress(final Address address) {
    final byte version = address.getVersion();

    final byte[] hash160 = address.getHash160();

    final byte[] checksum = Base58CheckUtil.computeChecksum(version, hash160);

    final byte[] combined = ArrayUtil.combine(new byte[] { version }, ArrayUtil.combine(hash160, checksum));

    return combined;
  }
}
