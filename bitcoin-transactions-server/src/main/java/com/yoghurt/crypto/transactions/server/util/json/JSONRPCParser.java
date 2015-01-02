package com.yoghurt.crypto.transactions.server.util.json;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;


public class JSONRPCParser {

  private JSONRPCParser() {}

  public static String getResultString(final InputStream is) throws JsonProcessingException, IOException {
    final JsonNode tree = JsonParser.mapper.readTree(is);

    return tree.get("result").getTextValue();
  }

  public static String getRawBlock(final InputStream jsonData) {
    return null;
  }
}
