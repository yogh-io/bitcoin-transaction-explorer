package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;
public class StartupPlace extends ApplicationPlace {
  public static class Tokenizer implements PlaceTokenizer<StartupPlace> {
    @Override
    public StartupPlace getPlace(final String token) {
      return new StartupPlace();
    }

    @Override
    public String getToken(final StartupPlace place) {
      return "home";
    }
  }
  public StartupPlace() {
  }
}
