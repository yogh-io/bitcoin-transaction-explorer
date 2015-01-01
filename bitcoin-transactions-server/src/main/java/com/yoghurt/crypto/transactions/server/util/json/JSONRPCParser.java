package com.yoghurt.crypto.transactions.server.util.json;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;


public class JSONRPCParser {

  private JSONRPCParser() {}

  public static String getRawTransactionHex(final InputStream jsonData) throws JsonProcessingException, IOException {
    final JsonNode tree = JsonParser.mapper.readTree(jsonData);

    return tree.get("result").getTextValue();
  }
}
