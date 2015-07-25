package com.yoghurt.crypto.transactions.shared.domain;

public enum JSONRPCMethod {
  GETBESTBLOCKHASH("getbestblockhash"),
  GETBLOCK("getblock"),
  GETBLOCKCHAININFO("getblockchaininfo"),
  GETBLOCKCOUNT("getblockcount"),
  GETBLOCKHASH("getblockhash"),
  GETCHAINTIPS("getchaintips"),
  GETDIFFICULTY("getdifficulty"),
  GETMEMPOOLINFO("getmempoolinfo"),
  GETTXOUT("gettxout"),
  GETTXOUTPROOF("gettxoutproof"),
  GETTXOUTSETINFO("gettxoutsetinfo"),
  GETINFO("getinfo"),
  HELP("help"),
  GETNETWORKINFO("getnetworkinfo"),
  GETNETTOTALS("getnettotals"),
  DECODERAWTRANSACTION("decoderawtransaction"),
  GETRAWTRANSACTION("getrawtransaction"),
  DECODESCRIPT("decodescript"),
  ESTIMATEFEE("estimatefee"),
  ESTIMATEPRIORITY("estimatepriority");

  private final String methodName;

  private JSONRPCMethod(final String methodName) {
    this.methodName = methodName;
  }

  /**
   * Return the JSONRPCMethod fromthe given name.
   *
   * @param name
   *          Name to get the method for.
   *
   * @return The JSONRPCMethod
   */
  public static JSONRPCMethod fromName(final String name) {
    // Currently, the method is the uppercase format of the name. If this ever
    // changes, this code breaks (well, it'll return null).
    // So be sure to change the code to account for it when/if this happens.
    try {
      return JSONRPCMethod.valueOf(name.toUpperCase());
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  public String getMethodName() {
    return methodName;
  }
}
