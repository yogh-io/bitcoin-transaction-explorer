package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.Prefix;

public class AddressPlace extends ApplicationPlace {
  private static final String PREFIX = "addr";

  public enum AddressDataType {
    HASH160("hash160"),

    ID("id");

    private final String token;

    private AddressDataType(final String token) {
      this.token = token;
    }

    public String getToken() {
      return token;
    }

    public static AddressDataType fromToken(final String token) {
      for (final AddressDataType type : values()) {
        if (type.getToken().equals(token)) {
          return type;
        }
      }

      return null;
    }
  }

  private final AddressDataType type;

  private final String payload;

  @Prefix(PREFIX)
  public static class Tokenizer extends DelimitedTokenizer<AddressPlace> {
    @Override
    protected AddressPlace createPlace(final String[] tokens) {
      final AddressDataType type = AddressDataType.fromToken(tokens[0]);

      return tokens.length == 1 ? new AddressPlace(type) : new AddressPlace(type, tokens[1]);
    }

    @Override
    protected String[] getTokens(final AddressPlace place) {
      return place.payload == null ? new String[] { place.getType().getToken() } : new String[] { place.getType().getToken(), place.getPayload() };
    }
  }

  public AddressPlace(final AddressDataType type) {
    this(type, null);
  }

  public AddressPlace(final AddressDataType type, final String payload) {
    this.type = type;
    this.payload = payload;
  }

  public AddressDataType getType() {
    return type;
  }

  public String getPayload() {
    return payload;
  }
}
