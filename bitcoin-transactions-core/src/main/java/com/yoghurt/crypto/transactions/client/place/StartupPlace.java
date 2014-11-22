package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
public class StartupPlace extends ApplicationPlace {
  private static final String PREFIX = "home";

  @Prefix(PREFIX)
  public static class Tokenizer implements PlaceTokenizer<StartupPlace> {
    @Override
    public StartupPlace getPlace(final String token) {
      return new StartupPlace();
    }

    @Override
    public String getToken(final StartupPlace place) {
      return "";
    }
  }
  public StartupPlace() {
  }
}
