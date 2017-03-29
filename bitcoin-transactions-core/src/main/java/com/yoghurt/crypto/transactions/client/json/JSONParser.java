package com.yoghurt.crypto.transactions.client.json;

import java.util.ArrayList;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.OutpointInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionState;

public class JSONParser {

  public static BlockInformation parseBlockInformation(JSONValue result) {
    JSONObject resultObject = result.isObject();

    BlockInformation blockInformation = new BlockInformation();

    blockInformation.setRawBlockHeaders(resultObject.get("rawBlockHeaders").isString().stringValue());
    blockInformation.setHeight((int) resultObject.get("height").isNumber().doubleValue());
    blockInformation.setNumConfirmations((int) resultObject.get("numConfirmations").isNumber().doubleValue());
    blockInformation.setNumTransactions((int) resultObject.get("numTransactions").isNumber().doubleValue());
    blockInformation.setSize((long) resultObject.get("size").isNumber().doubleValue());
    blockInformation.setNextBlockHash(resultObject.get("nextBlockHash").isString().stringValue());

    blockInformation.setCoinbaseInformation(parseTransactionInformation(resultObject.get("coinbaseInformation").isObject()));

    return blockInformation;
  }

  public static TransactionInformation parseTransactionInformation(JSONValue result) {
    JSONObject resultObject = result.isObject();

    TransactionInformation transactionInformation = new TransactionInformation();

    transactionInformation.setState(TransactionState.valueOf(resultObject.get("state").isString().stringValue()));
    transactionInformation.setTime((long) resultObject.get("time").isNumber().doubleValue());
    transactionInformation.setConfirmations((int) resultObject.get("confirmations").isNumber().doubleValue());
    transactionInformation.setFee((int) resultObject.get("fee").isNumber().doubleValue());
    transactionInformation.setRawHex(resultObject.get("rawHex").isString().stringValue());

    return transactionInformation;
  }

  public static AddressInformation parseAddressInformation(JSONValue result) {
    JSONArray resultArray = result.isObject().get("outpoints").isArray();

    AddressInformation addressInformation = new AddressInformation();

    ArrayList<OutpointInformation> outs = new ArrayList<>();

    for (int i = 0; i < resultArray.size(); i++) {
      JSONObject outpoint = resultArray.get(i).isObject();

      OutpointInformation info = new OutpointInformation();
      info.setIndex((int) outpoint.get("index").isNumber().doubleValue());
      info.setTransactionValue((long) outpoint.get("transactionValue").isNumber().doubleValue());
      info.setReferenceTransaction(outpoint.get("referenceTransaction").isString().stringValue().toUpperCase());
      info.setSpent(outpoint.get("spent").isBoolean().booleanValue());
      
      outs.add(info);
    }

    addressInformation.setOutpoints(outs);

    return addressInformation;
  }
}
