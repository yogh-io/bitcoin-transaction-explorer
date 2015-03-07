package com.yoghurt.crypto.transactions.shared.domain.config;

import java.io.Serializable;

import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;

public interface RetrievalHookConfig extends Serializable {
  BlockchainSource getBlockchainSource();
}
