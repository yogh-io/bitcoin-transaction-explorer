package com.yoghurt.crypto.transactions.server.util.json;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class JSONRPCEncoder {
  public static String getRequestString(final String method) throws JsonGenerationException, JsonMappingException, IOException {
    return getRequestString(method, new ArrayList<Object>());
  }

  public static String getRequestString(final String method, final ArrayList<Object> parameters) throws JsonGenerationException, JsonMappingException, IOException {
    final JSONRPCRequest req = new JSONRPCRequest(method);
    req.setParams(parameters);

    final StringWriter writer = new StringWriter();

    final ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Inclusion.NON_EMPTY);
    mapper.writeValue(writer, req);

    return writer.toString();
  }
}
