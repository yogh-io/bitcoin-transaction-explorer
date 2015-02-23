package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.Prefix;

public class MinePlace extends Place {
  private static final String PREFIX = "mine";

  public enum MineDataType {
    RAW("raw");

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

  public MinePlace() {
    this(MineDataType.RAW, "0100000000000000000000000000000000000000000000000000000000000000000000003BA3EDFD7A7B12B27AC72C3E67768F617FC81BC3888A51323A9FB8AA4B1E5E4A29AB5F49FFFF001D1DAC2B7C");
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
