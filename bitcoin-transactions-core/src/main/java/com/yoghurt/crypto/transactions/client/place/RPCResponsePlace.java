package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.Prefix;
import com.yoghurt.crypto.transactions.shared.domain.JSONRPCMethod;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;

public class RPCResponsePlace extends ApplicationPlace {
  private final String[] arguments;
  private final JSONRPCMethod method;

  public RPCResponsePlace(final String[] splitToken) {
    this(JSONRPCMethod.fromName(splitToken[0]), ArrayUtil.copyFrom(splitToken, 1));
  }

  public RPCResponsePlace(final JSONRPCMethod method, final String[] splitArguments) {
    this.method = method;
    this.arguments = splitArguments;
  }

  public JSONRPCMethod getMethod() {
    return method;
  }

  public String[] getArguments() {
    return arguments;
  }

  private static final String PREFIX = "json";

  @Prefix(PREFIX)
  public static class Tokenizer extends DelimitedTokenizer<RPCResponsePlace> {
    @Override
    protected RPCResponsePlace createPlace(final String[] tokens) {
      return new RPCResponsePlace(tokens);
    }

    @Override
    protected String[] getTokens(final RPCResponsePlace place) {
      final String[] tokens = new String[place.arguments.length + 1];

      tokens[0] = place.getMethod().getMethodName();
      System.arraycopy(place.arguments, 0, tokens, 1, place.arguments.length);

      return tokens;
    }
  }
}
