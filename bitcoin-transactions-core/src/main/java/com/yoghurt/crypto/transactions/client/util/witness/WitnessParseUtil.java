package com.yoghurt.crypto.transactions.client.util.witness;

import java.util.ArrayList;

import com.yoghurt.crypto.transactions.shared.domain.VariableLengthInteger;
import com.yoghurt.crypto.transactions.shared.domain.WitnessEntity;
import com.yoghurt.crypto.transactions.shared.domain.WitnessPart;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;

public final class WitnessParseUtil {
  private WitnessParseUtil() {
  }

  public static int parseWitness(final int initialPointer, final WitnessEntity witness, final byte[] bytes) {
    int pointer = initialPointer;

    VariableLengthInteger witnessItemLength = new VariableLengthInteger(bytes, pointer);
    pointer += witnessItemLength.getByteSize();
    witness.setItemsLength(witnessItemLength);
    
    ArrayList<WitnessPart> parts = new ArrayList<WitnessPart>();
    for (int i = 0; i < witnessItemLength.getValue(); i++) {
      WitnessPart part = new WitnessPart();

      VariableLengthInteger witnessItemSize = new VariableLengthInteger(bytes, pointer);
      part.setLength(witnessItemSize);
      
      byte[] partBytes = ArrayUtil.arrayCopy(bytes, pointer += witnessItemSize.getByteSize(), pointer += witnessItemSize.getValue());

      part.setData(partBytes);

      parts.add(part);
    }

    witness.setPushDatas(parts);

    return pointer;
  }
}
