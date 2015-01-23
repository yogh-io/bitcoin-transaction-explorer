package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.Prefix;

public class ScriptPlace extends ApplicationPlace {
  private static final String PREFIX = "script";

  public enum ScriptDataType {
    RAW("raw"),

    ID("id");

    private final String token;

    private ScriptDataType(final String token) {
      this.token = token;
    }

    public String getToken() {
      return token;
    }

    public static ScriptDataType fromToken(final String token) {
      for (final ScriptDataType type : values()) {
        if (type.getToken().equals(token)) {
          return type;
        }
      }

      return null;
    }
  }

  private final ScriptDataType type;

  private String outpointTransaction;
  private int outpointIndex;
  private String scriptSig;
  private String pubKeySig;

  @Prefix(PREFIX)
  public static class Tokenizer extends DelimitedTokenizer<ScriptPlace> {
    @Override
    protected ScriptPlace createPlace(final String[] tokens) {
      final ScriptDataType type = ScriptDataType.fromToken(tokens[0]);
      final ScriptPlace place = new ScriptPlace(type);

      switch (type) {
      case ID:
        place.setOutpointTransaction(tokens[1]);
        place.setOutpointIndex(Integer.parseInt(tokens[2]));
        place.setScriptSig(tokens[3]);
        break;
      case RAW:
        // Outpoint script
        place.setPubKeySig(tokens[2]);

        // Input script
        place.setScriptSig(tokens[1]);
        break;
      default:
        return null;
      }

      return place;
    }

    @Override
    protected String[] getTokens(final ScriptPlace place) {
      switch (place.getType()) {
      case ID:
        return new String[] {
            place.getType().getToken(),
            place.getOutpointTransaction(),
            String.valueOf(place.getOutpointIndex()),
            place.getScriptSig()
        };
      case RAW:
        return new String[] {
            place.getType().getToken(),
            place.getPubKeySig(),
            place.getScriptSig()
        };
      default:
        return null;
      }
    }
  }

  public ScriptPlace(final ScriptDataType type) {
    this.type = type;
  }

  public ScriptPlace(final String outpointTransaction, final int outpointIndex, final String scriptSig) {
    this.type = ScriptDataType.ID;
    this.outpointTransaction = outpointTransaction;
    this.outpointIndex = outpointIndex;
    this.scriptSig = scriptSig;
  }

  public ScriptPlace(final String outpointTransaction, final String pubKeySig, final String scriptSig) {
    this.type = ScriptDataType.RAW;
    this.scriptSig = scriptSig;
    this.pubKeySig = pubKeySig;
  }

  public void setPubKeySig(final String pubKeySig) {
    this.pubKeySig = pubKeySig;
  }

  public void setOutpointIndex(final int outpointIndex) {
    this.outpointIndex = outpointIndex;
  }

  public void setOutpointTransaction(final String outpointTransaction) {
    this.outpointTransaction = outpointTransaction;
  }

  public void setScriptSig(final String scriptSig) {
    this.scriptSig = scriptSig;
  }

  public ScriptDataType getType() {
    return type;
  }

  public String getScriptSig() {
    return scriptSig;
  }

  public String getOutpointTransaction() {
    return outpointTransaction;
  }

  public int getOutpointIndex() {
    return outpointIndex;
  }

  public String getPubKeySig() {
    return pubKeySig;
  }
}