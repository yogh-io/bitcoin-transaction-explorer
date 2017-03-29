package com.yoghurt.crypto.transactions.client.service;

import java.util.ArrayList;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrieveMethod;

public class BlockchainRetrievalServiceAsyncImpl implements BlockchainRetrievalServiceAsync {
  public BlockchainRetrievalServiceAsyncImpl() {
  }

  @Override
  public void getTransactionInformation(String txid, AsyncCallback<TransactionInformation> callback) {
    doRequest(new TransactionInformationParseCallback(callback), BlockchainRetrieveMethod.GET_TRANSACTION_INFORMATION, txid);
  }

  @Override
  public void getBlockInformationFromHash(String blockHash, AsyncCallback<BlockInformation> callback) {
    doRequest(new BlockInformationParseCallback(callback), BlockchainRetrieveMethod.GET_BLOCK_INFORMATION_FROM_HASH, blockHash);
  }

  @Override
  public void getBlockInformationFromHeight(int height, AsyncCallback<BlockInformation> callback) {
    doRequest(new BlockInformationParseCallback(callback), BlockchainRetrieveMethod.GET_BLOCK_INFORMATION_FROM_HEIGHT, height);
  }

  @Override
  public void getBlockInformationLast(AsyncCallback<BlockInformation> callback) {
    doRequest(new BlockInformationParseCallback(callback), BlockchainRetrieveMethod.GET_BLOCK_INFORMATION_LAST);
  }

  @Override
  public void getLatestBlockHash(AsyncCallback<String> callback) {
    doRequest(new ForwardedAsyncCallback<String, JSONValue>(callback) {
      @Override
      public void onSuccess(JSONValue result) {
        callback.onSuccess(result.isString().stringValue());
      }
    }, BlockchainRetrieveMethod.GET_LATEST_BLOCK_HASH);
  }

  @Override
  public void getAddressInformation(String address, AsyncCallback<AddressInformation> callback) {
    doRequest(new AddressInformationParseCallback(callback), BlockchainRetrieveMethod.GET_ADDRESS_INFORMATION, address);
  }

  @Override
  public void getTransactionList(int height, AsyncCallback<ArrayList<String>> callback) {
    doRequest(new ForwardedAsyncCallback<ArrayList<String>, JSONValue>(callback) {
      @Override
      public void onSuccess(JSONValue result) {
        ArrayList<String> txs = new ArrayList<>();

        JSONArray arr = result.isArray();
        for (int i = 0; i < arr.size(); i++) {
          txs.add(arr.get(i).isString().stringValue());
        }

        callback.onSuccess(txs);
      }
    }, BlockchainRetrieveMethod.GET_TRANSACTION_LIST, height);
  }

  private void doRequest(AsyncCallback<JSONValue> callback, BlockchainRetrieveMethod method, Object... params) {
    RequestUtil.doGet("/application/blockchain-retrieve" + getParams(method, params), callback);
  }

  private String getParams(BlockchainRetrieveMethod method, Object... params) {
    String formattedParams = formatParams(params);

    return "?method=" + method.getName() + (formattedParams.isEmpty() ? "" : "&" + formattedParams);
  }

  private String formatParams(Object[] params) {
    if (params.length == 0) {
      return "";
    }

    // Only support first param for now
    return "param=" + String.valueOf(params[0]);
  }
}