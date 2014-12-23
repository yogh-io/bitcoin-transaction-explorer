package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.Prefix;

public class MinePlace extends ApplicationPlace {
  private static final String PREFIX = "mine";

  public enum MineDataType {
    RAW("raw"),

    ID("id"),

    LAST("last"),

    HEIGHT("height");

    private final String token;

    private MineDataType(final String token) {
      this.token = token;
    }

    public String getToken() {
      return token;
    }

    public static MineDataType fromToken(final String token) {
      for (final MineDataType type : values()) {
        if (type.getToken().equals(token)) {
          return type;
        }
      }

      return null;
    }
  }

  private final MineDataType type;

  private final String payload;

  @Prefix(PREFIX)
  public static class Tokenizer extends DelimitedTokenizer<MinePlace> {
    @Override
    protected MinePlace createPlace(final String[] tokens) {
      final MineDataType type = MineDataType.fromToken(tokens[0]);

      return tokens.length == 1 ? new MinePlace(type) : new MinePlace(type, tokens[1]);
    }

    @Override
    protected String[] getTokens(final MinePlace place) {
      return place.payload == null ? new String[] { place.getType().getToken() } : new String[] { place.getType().getToken(), place.getPayload() };
    }
  }

  public MinePlace(final MineDataType type) {
    this(type, null);
  }

  public MinePlace(final MineDataType type, final int height) {
    this(type, String.valueOf(height));
  }

  public MinePlace(final MineDataType type, final String payload) {
    this.type = type;
    this.payload = payload;
  }

  public MineDataType getType() {
    return type;
  }

  public String getPayload() {
    return payload;
  }
}
