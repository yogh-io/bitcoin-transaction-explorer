package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.Prefix;

public class TransactionPlace extends ApplicationPlace {
  private static final String PREFIX = "tx";

  public enum TransactionType {
    RAW("raw"),

    ID("id");

    private final String token;

    private TransactionType(final String token) {
      this.token = token;
    }

    public String getToken() {
      return token;
    }

    public static TransactionType fromToken(final String token) {
      for (final TransactionType type : values()) {
        if (type.getToken().equals(token)) {
          return type;
        }
      }

      return null;
    }
  }

  private final TransactionType type;
  private final String hex;

  @Prefix(PREFIX)
  public static class Tokenizer extends DelimitedTokenizer<TransactionPlace> {
    @Override
    protected TransactionPlace createPlace(final String[] tokens) {
      final TransactionType type = TransactionType.fromToken(tokens[0]);

      return new TransactionPlace(type, tokens[1]);
    }

    @Override
    protected String[] getTokens(final TransactionPlace place) {
      return new String[] { place.getType().getToken(), place.getHex() };
    }
  }

  public TransactionPlace(final TransactionType type, final String hex) {
    this.type = type;
    this.hex = hex;
  }

  public TransactionType getType() {
    return type;
  }

  public String getHex() {
    return hex;
  }
}
