package com.yoghurt.crypto.transactions.client.i18n;

import com.google.gwt.core.client.GWT;

/**
 * Global class to messages interfaces.
 */
public final class M {
  private static final ApplicationMessages APPLICATION_MESSAGES = GWT.create(ApplicationMessages.class);

  public static ApplicationMessages messages() {
    return APPLICATION_MESSAGES;
  }
}
