package com.yoghurt.crypto.transactions.server.domain;

import java.io.Serializable;

import com.yoghurt.crypto.transactions.shared.service.domain.ApplicationContextBase;

public interface AdministratedApplicationConfig extends ApplicationContextBase, Serializable {
  BlockchainSource getBlockchainSource();
}
