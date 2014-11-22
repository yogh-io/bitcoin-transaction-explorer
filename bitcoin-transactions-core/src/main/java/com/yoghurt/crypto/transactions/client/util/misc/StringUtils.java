package com.yoghurt.crypto.transactions.client.util.misc;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public class StringUtils {
  public static String join(final String... text) {
    return join("", text);
  }

  public static String join(final String delimiter, final String... text) {
    final JsArrayString jsa = JavaScriptObject.createArray().cast();

    for (int i = 0; i < text.length; i++) {
      jsa.push(text[i]);
    }

    return jsa.join(delimiter);
  }
}
