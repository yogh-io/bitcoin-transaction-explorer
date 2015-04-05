package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ContributePlace extends ApplicationPlace {
  private static final String PREFIX = "contribute";

  @Prefix(PREFIX)
  public static class Tokenizer implements PlaceTokenizer<ContributePlace> {
    @Override
    public ContributePlace getPlace(final String token) {
      return new ContributePlace();
    }

    @Override
    public String getToken(final ContributePlace place) {
      return "";
    }
  }
}
