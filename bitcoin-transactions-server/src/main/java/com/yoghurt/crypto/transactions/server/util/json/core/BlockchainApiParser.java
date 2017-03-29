package com.yoghurt.crypto.transactions.server.util.json.core;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

import com.yoghurt.crypto.transactions.server.util.json.JsonParser;

public class BlockchainApiParser {

  public static String parseLatestBlockHash(final InputStream jsonData) throws JsonProcessingException, IOException {
    final JsonNode tree = JsonParser.readTree(jsonData);

    return tree.get("hash").getTextValue();
  }

}
