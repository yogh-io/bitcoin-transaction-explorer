package com.yoghurt.crypto.transactions.client.domain.transaction;

public enum TransactionPartType {
  VERSION,

  INPUT_SIZE,

  INPUT_OUTPOINT_HASH,

  INPUT_OUTPOINT_INDEX,

  INPUT_SCRIPT_LENGTH,

  INPUT_SIGNATURE_SCRIPT,

  INPUT_SEQUENCE,

  OUTPUT_SIZE,

  OUTPUT_SCRIPT_LENGTH,

  OUTPUT_SIGNATURE_SCRIPT,

  OUTPUT_VALUE,

  LOCK_TIME,
}
