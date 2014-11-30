package com.yoghurt.crypto.transactions.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class FormatUtil {
  private static final DateTimeFormat DATE_TIME_FORMATTER = DateTimeFormat.getFormat("MMMM dd, yyyy, HH:mm:ss Z");

  public static String formatDateTime(final Date time) {
    return DATE_TIME_FORMATTER.format(time);
  }

}
