package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.Prefix;

public class TransactionPlace extends ApplicationPlace {
  private static final String PREFIX = "tx";

  public enum TransactionDataType {
    RAW("raw"),

    ID("id");

    private final String token;

    private TransactionDataType(final String token) {
      this.token = token;
    }

    public String getToken() {
      return token;
    }

    public static TransactionDataType fromToken(final String token) {
      for (final TransactionDataType type : values()) {
        if (type.getToken().equals(token)) {
          return type;
        }
      }

      return null;
    }
  }

  private final TransactionDataType type;
  private final String hex;

  @Prefix(PREFIX)
  public static class Tokenizer extends DelimitedTokenizer<TransactionPlace> {
    @Override
    protected TransactionPlace createPlace(final String[] tokens) {
      final TransactionDataType type = TransactionDataType.fromToken(tokens[0]);

      return new TransactionPlace(type, tokens[1]);
    }

    @Override
    protected String[] getTokens(final TransactionPlace place) {
      return new String[] { place.getType().getToken(), place.getHex() };
    }
  }

  public TransactionPlace(final TransactionDataType type, final String hex) {
    this.type = type;
    this.hex = hex;
  }

  public TransactionDataType getType() {
    return type;
  }

  public String getHex() {
    return hex;
  }
}
