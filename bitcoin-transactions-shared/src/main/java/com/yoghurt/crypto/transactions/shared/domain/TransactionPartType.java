package com.yoghurt.crypto.transactions.shared.domain;

public enum TransactionPartType {
  VERSION,

  INPUT_SIZE,

  INPUT_PREVIOUS_TRANSACTION_HASH,

  INPUT_OUTPOINT_INDEX,

  INPUT_SCRIPT_LENGTH,

  INPUT_SEQUENCE,

  OUTPUT_SIZE,

  OUTPUT_SCRIPT_LENGTH,

  OUTPUT_VALUE,

  LOCK_TIME,

  SCRIPT_SIG_OP_CODE,

  SCRIPT_SIG_PUSH_DATA,

  SCRIPT_PUB_KEY_OP_CODE,

  SCRIPT_PUB_KEY_PUSH_DATA,

  /**
   * We'll be special-casing the coinbase input scriptsig because it cannot be interpreted as usual.
   */
  COINBASE_SCRIPT_SIG,

  /**
   * Explicit fee
   */
  FEE,

  FEDERATED_SIG_PUSH_DATA,

  FEDERATED_SIG_OP_CODE;
}
