package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ConfigPlace extends ApplicationPlace {
  private static final String PREFIX = "config";

  @Prefix(PREFIX)
  public static class Tokenizer implements PlaceTokenizer<ConfigPlace> {
    @Override
    public ConfigPlace getPlace(final String token) {
      return new ConfigPlace();
    }

    @Override
    public String getToken(final ConfigPlace place) {
      return "";
    }
  }
}
