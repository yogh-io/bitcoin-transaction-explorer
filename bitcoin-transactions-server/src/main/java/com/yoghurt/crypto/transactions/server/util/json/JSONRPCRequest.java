package com.yoghurt.crypto.transactions.server.util.json;

import java.util.ArrayList;

public class JSONRPCRequest {
  private String method;
  private ArrayList<Object> params;

  public JSONRPCRequest(final String method) {
    this.method = method;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(final String method) {
    this.method = method;
  }

  public ArrayList<Object> getParams() {
    return params;
  }

  public void setParams(final ArrayList<Object> params) {
    this.params = params;
  }
}
