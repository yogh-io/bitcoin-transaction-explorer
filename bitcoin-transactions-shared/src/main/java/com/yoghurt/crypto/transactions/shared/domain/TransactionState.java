package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum TransactionState implements Serializable, IsSerializable {
  CONFIRMED, UNCONFIRMED
}
