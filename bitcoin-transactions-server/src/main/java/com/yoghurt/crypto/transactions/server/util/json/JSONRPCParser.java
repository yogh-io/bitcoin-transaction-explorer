package com.yoghurt.crypto.transactions.server.util.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

import com.yoghurt.crypto.transactions.server.util.NumberEncodeUtil;
import com.yoghurt.crypto.transactions.shared.service.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.service.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.service.domain.OutpointInformation;
import com.yoghurt.crypto.transactions.shared.service.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.service.domain.TransactionState;
import com.yoghurt.crypto.transactions.shared.service.util.ArrayUtil;

public class JSONRPCParser {
  private static final String ZERO_HASH = "0000000000000000000000000000000000000000000000000000000000000000";

  private JSONRPCParser() {
  }

  public static String getString(final InputStream jsonData) throws JsonProcessingException, IOException {
    final JsonNode tree = JsonParser.mapper.readTree(jsonData);

    return tree.getTextValue();
  }

  public static String getResultString(final InputStream jsonData) throws JsonProcessingException, IOException {
    final JsonNode tree = JsonParser.mapper.readTree(jsonData);

    return tree.get("result").getTextValue();
  }

  private static JsonNode getResultNode(final InputStream jsonData) throws JsonProcessingException, IOException {
    return JsonParser.mapper.readTree(jsonData).get("result");
  }

  public static ArrayList<String> getTransactionList(final InputStream jsonData) throws JsonProcessingException, IOException {
    final JsonNode tree = getResultNode(jsonData);

    // Array of txs
    final ArrayList<String> txs = new ArrayList<>();
    final JsonNode txNode = tree.get("tx");
    for (int i = 0; i < txNode.size(); i++) {
      txs.add(txNode.get(i).getTextValue());
    }

    return txs;
  }

  public static BlockInformation getBlockInformation(final InputStream jsonData) throws JsonProcessingException, IOException, DecoderException {
    final JsonNode tree = getResultNode(jsonData);

    // Create a builder to assemble the block headers
    final StringBuilder builder = new StringBuilder();

    // Version
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(tree.get("version").getLongValue())));

    // Prev block hash (LE<>BE)
    final JsonNode prevBlockHashNode = tree.get("previousblockhash");

    // prevBlockHashNode is null for the genesis block
    byte[] prevBlockHash;
    if (prevBlockHashNode == null) {
      prevBlockHash = Hex.decodeHex(ZERO_HASH.toCharArray());
    } else {
      prevBlockHash = Hex.decodeHex(prevBlockHashNode.getTextValue().toCharArray());
    }
    ArrayUtil.reverse(prevBlockHash);
    builder.append(Hex.encodeHex(prevBlockHash));

    // Merkle root (LE<>BE)
    final byte[] merkleroot = Hex.decodeHex(tree.get("merkleroot").getTextValue().toCharArray());
    ArrayUtil.reverse(merkleroot);
    builder.append(Hex.encodeHex(merkleroot));

    // Timestamp
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(tree.get("time").getLongValue())));

    // Bits (LE<>BE)
    final byte[] bits = Hex.decodeHex(tree.get("bits").getTextValue().toCharArray());
    ArrayUtil.reverse(bits);
    builder.append(Hex.encodeHex(bits));

    // Nonce
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(tree.get("nonce").getLongValue())));

    // Create a BlockInformation object to store the block information in
    final BlockInformation blockInformation = new BlockInformation();

    // Set the raw block headers
    blockInformation.setRawBlockHeaders(builder.toString());

    // Set the height
    blockInformation.setHeight(tree.get("height").getIntValue());

    // Set the next block hash, if any
    final JsonNode nextBlockHashNode = tree.get("nextblockhash");
    blockInformation.setNextBlockHash(nextBlockHashNode == null ? "" : nextBlockHashNode.getTextValue());

    // Set the number of confirmations
    blockInformation.setNumConfirmations(tree.get("confirmations").getIntValue());

    // Set the number of transactions
    blockInformation.setNumTransactions(tree.get("tx").size());

    // Set the byte size
    blockInformation.setSize(tree.get("size").getLongValue());

    // Set the raw coinbase transaction to its txid (this is a work-around, see
    // TODO in BitcoinJSONRPCRetriever)
    final TransactionInformation ti = new TransactionInformation();
    ti.setRawHex(tree.get("tx").get(0).getTextValue());
    blockInformation.setCoinbaseInformation(ti);

    return blockInformation;
  }

  public static TransactionInformation getTransactionInformation(final InputStream jsonData) throws JsonProcessingException, IOException {
    final JsonNode tree = getResultNode(jsonData);

    final TransactionInformation transactionInformation = new TransactionInformation();

    final JsonNode confirmationsNode = tree.get("confirmations");

    if (confirmationsNode == null) {
      transactionInformation.setConfirmations(0);
      transactionInformation.setState(TransactionState.UNCONFIRMED);
    } else {
      transactionInformation.setConfirmations(confirmationsNode.getIntValue());
      transactionInformation.setState(TransactionState.CONFIRMED);
      transactionInformation.setTime(new Date(tree.get("time").getLongValue() * 1000));
      transactionInformation.setBlockHash(tree.get("blockhash").getTextValue());
    }

    transactionInformation.setRawHex(tree.get("hex").getTextValue());

    return transactionInformation;
  }

  public static AddressInformation getAddressInformation(final String address, final InputStream jsonData)
      throws JsonProcessingException, IOException, DecoderException {
    final JsonNode tree = getResultNode(jsonData);

    final ArrayList<OutpointInformation> outpoints = new ArrayList<>();

    for (final JsonNode tx : tree) {
      final byte[] txId = Hex.decodeHex(tx.get("txid").getTextValue().toCharArray());

      for (final JsonNode vout : tx.get("vout")) {
        final JsonNode voutScriptPubKey = vout.get("scriptPubKey");
        final JsonNode voutAddresses = voutScriptPubKey.get("addresses");
        for (final JsonNode voutAddress : voutAddresses) {
          if (address.equals(voutAddress.getTextValue())) {
            final OutpointInformation outpoint = new OutpointInformation();

            final int idx = vout.get("n").getIntValue();
            final byte[] script = Hex.decodeHex(voutScriptPubKey.get("hex").getTextValue().toCharArray());

            outpoint.setIndex(idx);
            outpoint.setReferenceTransaction(txId);
            outpoint.setTransactionValue((long) (vout.get("value").getDoubleValue() * 100000000d));

            outpoint.setOutputScript(script);

            outpoints.add(outpoint);
          }
        }
      }
    }

    final AddressInformation ai = new AddressInformation();
    ai.setOutpoints(outpoints);

    return ai;
  }

  public static boolean isNullResult(final InputStream jsonData) throws JsonProcessingException, IOException {
    return getResultNode(jsonData).isNull();
  }
}
