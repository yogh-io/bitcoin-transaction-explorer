package com.yoghurt.crypto.transactions.shared.domain;

public enum BlockPartType {
  VERSION,

  PREV_BLOCK_HASH,

  MERKLE_ROOT,

  TIMESTAMP,

  BITS
}
