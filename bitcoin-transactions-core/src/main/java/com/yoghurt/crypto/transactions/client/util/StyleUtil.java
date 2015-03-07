package com.yoghurt.crypto.transactions.client.util;

import com.google.gwt.user.client.ui.Widget;

public final class StyleUtil {
  private StyleUtil() {}

  public static void setPlaceHolder(final Widget lookupField, final String text) {
    lookupField.getElement().setPropertyString("placeholder", text);
  }
}