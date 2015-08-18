package com.yoghurt.crypto.transactions.client.util.address;

import com.yoghurt.crypto.transactions.shared.domain.Address;
import com.yoghurt.crypto.transactions.shared.domain.Address.TYPE;

public class AddressParseUtil {

  public static Base58CheckContents parseAddress(final String addressText) {
    return Base58CheckUtil.parseBase58Check(addressText);
  }

  public static byte[] getChecksum(final Address address) {
    return Base58CheckUtil.computeChecksum(address.getVersion(), address.getHash160());
  }

  public static Address parseAddress(final Base58CheckContents contents) {
    final Address address = new Address();

    switch(contents.getVersion() & 0xFF) {
    case 0x01:
      address.setType(TYPE.P2PKH);
      break;
    case 0x03:
      address.setType(TYPE.P2SH);
      break;
    }

    address.setVersion(contents.getVersion());
    address.setHash160(contents.getPayload());

    return address;
  }

  public static boolean isValid(final Base58CheckContents contents) {
    return Base58CheckUtil.isValid(contents);
  }
}
