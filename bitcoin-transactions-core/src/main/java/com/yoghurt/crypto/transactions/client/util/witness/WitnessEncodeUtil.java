package com.yoghurt.crypto.transactions.client.util.witness;

import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.ScriptType;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;
import com.yoghurt.crypto.transactions.shared.domain.WitnessEntity;
import com.yoghurt.crypto.transactions.shared.domain.WitnessPart;

public final class WitnessEncodeUtil {
  private WitnessEncodeUtil() {
  }

  public static void encodeWitnesses(WitnessEntity witness, RawTransactionContainer container) {
    for (WitnessPart part : witness.getPushDatas()) {
      container.add(TransactionPartType.WITNESS_PUSH_DATA_LENGTH, part.getLength().getBytes());
      container.add(TransactionPartType.WITNESS_PUSH_DATA, part.getData());
    }
  }
}
