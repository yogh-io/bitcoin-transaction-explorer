package com.yoghurt.crypto.transactions.server.servlets.util.json;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

class JsonParser {
  protected static ObjectMapper mapper;
  static {
    mapper = new ObjectMapper();
    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
}