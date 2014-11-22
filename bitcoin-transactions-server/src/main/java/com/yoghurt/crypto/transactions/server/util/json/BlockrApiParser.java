package com.yoghurt.crypto.transactions.server.util.json;

import java.io.IOException;

import org.apache.commons.codec.DecoderException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

public final class BlockrApiParser {
  private static final String STATUS_SUCCESS = "success";

  private BlockrApiParser() {}

  public static String getRawTransactionHex(final byte[] data) throws JsonProcessingException, IOException, DecoderException {
    final JsonNode tree = JsonParser.mapper.readTree(data);

    final String status = tree.get("status").getTextValue();

    if(!STATUS_SUCCESS.equals(status)) {
      throw new IllegalStateException("JSON response does not indicate success.");
    }

    return tree.get("data").get("tx").get("hex").getTextValue();
  }
}
