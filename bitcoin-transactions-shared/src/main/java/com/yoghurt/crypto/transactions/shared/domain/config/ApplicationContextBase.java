package com.yoghurt.crypto.transactions.shared.domain.config;

import java.io.Serializable;

public interface ApplicationContextBase extends Serializable {
  String getApplicationTitle();

  String getApplicationSubTitle();
}
