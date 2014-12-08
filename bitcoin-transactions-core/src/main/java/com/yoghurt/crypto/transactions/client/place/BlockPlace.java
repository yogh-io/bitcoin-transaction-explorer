package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.Prefix;

public class BlockPlace extends ApplicationPlace {
  private static final String PREFIX = "block";

  public enum BlockDataType {
    RAW("raw"),

    ID("id"),

    HEIGHT("height"),

    LAST("last");

    private final String token;

    private BlockDataType(final String token) {
      this.token = token;
    }

    public String getToken() {
      return token;
    }

    public static BlockDataType fromToken(final String token) {
      for (final BlockDataType type : values()) {
        if (type.getToken().equals(token)) {
          return type;
        }
      }

      return null;
    }
  }

  private final BlockDataType type;

  private final String payload;

  @Prefix(PREFIX)
  public static class Tokenizer extends DelimitedTokenizer<BlockPlace> {
    @Override
    protected BlockPlace createPlace(final String[] tokens) {
      final BlockDataType type = BlockDataType.fromToken(tokens[0]);

      return tokens.length == 1 ? new BlockPlace(type) : new BlockPlace(type, tokens[1]);
    }

    @Override
    protected String[] getTokens(final BlockPlace place) {
      return place.payload == null ? new String[] { place.getType().getToken() } : new String[] { place.getType().getToken(), place.getPayload() };
    }
  }

  public BlockPlace(final BlockDataType type) {
    this(type, null);
  }

  public BlockPlace(final BlockDataType type, final int height) {
    this(type, String.valueOf(height));
  }

  public BlockPlace(final BlockDataType type, final String payload) {
    this.type = type;
    this.payload = payload;
  }

  public BlockDataType getType() {
    return type;
  }

  public String getPayload() {
    return payload;
  }
}
