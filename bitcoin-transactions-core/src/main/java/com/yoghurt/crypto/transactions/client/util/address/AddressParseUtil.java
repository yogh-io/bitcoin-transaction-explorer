package com.yoghurt.crypto.transactions.client.util.address;

import java.util.ArrayList;

import com.yoghurt.crypto.transactions.shared.domain.Address;
import com.yoghurt.crypto.transactions.shared.domain.Address.TYPE;
import com.yoghurt.crypto.transactions.shared.domain.Base58CheckContents;
import com.yoghurt.crypto.transactions.shared.domain.Operation;
import com.yoghurt.crypto.transactions.shared.domain.ScriptPart;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutput;

public class AddressParseUtil {
  private static final Operation[] P2PKH_TEMPLATE = new Operation[] { Operation.OP_DUP, Operation.OP_HASH160, Operation.OP_PUSHDATA, Operation.OP_EQUALVERIFY, Operation.OP_CHECKSIG };
  private static final Operation[] P2SH_TEMPLATE = new Operation[] { Operation.OP_HASH160, Operation.OP_PUSHDATA, Operation.OP_EQUAL };

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

  public static Address tryParseAddress(final TransactionOutput output) {
    if(matchesTemplate(output.getInstructions(), P2PKH_TEMPLATE)) {
      final Address p2pkh = new Address();
      p2pkh.setType(TYPE.P2PKH);
      p2pkh.setVersion((byte) 0);
      p2pkh.setHash160(output.getInstructions().get(2).getBytes());
      return p2pkh;
    } else if (matchesTemplate(output.getInstructions(), P2SH_TEMPLATE)) {
      final Address p2sh = new Address();
      p2sh.setType(TYPE.P2SH);
      p2sh.setVersion((byte) 5);
      p2sh.setHash160(output.getInstructions().get(1).getBytes());
      return p2sh;
    }

    return null;
  }

  private static boolean matchesTemplate(final ArrayList<ScriptPart> instructions, final Operation[] template) {
    if(instructions.size() != template.length) {
      return false;
    }

    int counter = 0;

    for(final ScriptPart part : instructions) {
      if(part.getOperation() != template[counter]) {
        return false;
      }

      counter++;
    }

    return true;
  }
}
