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

  public static String getRequestStringWithParameters(final String method, final ArrayList<Object> parameters) throws JsonGenerationException, JsonMappingException, IOException {
    final JSONRPCRequest req = new JSONRPCRequest(method);
    req.setParams(parameters);

    final StringWriter writer = new StringWriter();

    final ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Inclusion.NON_EMPTY);
    mapper.writeValue(writer, req);

    return writer.toString();
  }

  /**
   * TODO If we could get the nasty pre-serialization stuff into the serialization policy, that'd be great.
   */
  public static String getRequestString(final String method, final Object... parameters) throws JsonGenerationException, JsonMappingException, IOException {
    final ArrayList<Object> parameterLst = new ArrayList<>();

    for (Object parameter : parameters) {
      // Just check if this parameter is a number, if it is, make it an explicit
      // number
      if (parameter instanceof String) {
        if (((String) parameter).toLowerCase().equals("true")) {
          parameter = true;
        } else if (((String) parameter).toLowerCase().equals("false")) {
          parameter = false;
        } else if (parameter instanceof String) {
          try {
            parameter = Integer.parseInt((String) parameter);
          } catch (final NumberFormatException e) {
            // eat
          }
        }
      }

      parameterLst.add(parameter);
    }

    return getRequestStringWithParameters(method, parameterLst);
  }
}
