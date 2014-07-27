package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

public class TransactionBreakdownPlace extends ApplicationPlace {
  private final String hex;

  public static class Tokenizer implements PlaceTokenizer<TransactionBreakdownPlace> {
    @Override
    public TransactionBreakdownPlace getPlace(final String token) {
      return new TransactionBreakdownPlace(token);
    }

    @Override
    public String getToken(final TransactionBreakdownPlace place) {
      return place.getHex();
    }
  }

  public TransactionBreakdownPlace(final String hex) {
    this.hex = hex;
  }

  public String getHex() {
    return hex;
  }
}
