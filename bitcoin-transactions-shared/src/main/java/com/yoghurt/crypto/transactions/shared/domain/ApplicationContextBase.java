package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

public interface ApplicationContextBase extends Serializable {
  String getApplicationTitle();

  String getApplicationSubTitle();
}