package com.yoghurt.crypto.transactions.shared.domain.config;

import java.io.Serializable;

import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;

public interface AdministratedApplicationConfig extends ApplicationContextBase, Serializable {
  BlockchainSource getBlockchainSource();
}
