package com.yoghurt.crypto.transactions.server.util.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionState;
import com.yoghurt.crypto.transactions.shared.domain.VariableLengthInteger;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;
import com.yoghurt.crypto.transactions.shared.util.NumberEncodeUtil;

public class JSONRPCParser {
  private static final String ZERO_HASH = "0000000000000000000000000000000000000000000000000000000000000000";

  private JSONRPCParser() {}

  public static String getResultString(final InputStream jsonData) throws JsonProcessingException, IOException {
    final JsonNode tree = JsonParser.mapper.readTree(jsonData);

    return tree.get("result").getTextValue();
  }

  private static JsonNode getResultNode(final InputStream jsonData) throws JsonProcessingException, IOException {
    return JsonParser.mapper.readTree(jsonData).get("result");
  }

  public static String getBitsValue(InputStream jsonData) throws JsonProcessingException, IOException, DecoderException {
    final String rawBlockString = getResultString(jsonData);

    final int bitsStart = (4 + 32 + 32 + 4) * 2;
    final VariableLengthInteger bitsLength = new VariableLengthInteger(Hex.decodeHex(rawBlockString.substring(bitsStart, bitsStart + 8).toCharArray()));

    System.out.println(bitsLength.getValue());
    System.out.println(rawBlockString);

    return rawBlockString.substring(bitsStart, (int) (bitsStart + (bitsLength.getByteSize() * 2) + (bitsLength.getValue() * 2)));
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

    // Skip the bits value, it'll be retrieved later (it's in verbose form here)

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
    blockInformation.setNextBlockHash(nextBlockHashNode == null ? null : nextBlockHashNode.getTextValue());

    // Set the number of confirmations
    blockInformation.setNumConfirmations(tree.get("confirmations").getIntValue());

    // Set the number of transactions
    blockInformation.setNumTransactions(tree.get("tx").size());

    // Set the byte size
    blockInformation.setSize(tree.get("size").getLongValue());

    // Set the raw coinbase transaction to its txid (this is a work-around, see
    // TODO in BitcoinJSONRPCRetriever)
    blockInformation.setRawCoinbaseTransaction(tree.get("tx").get(0).getTextValue());

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

    return transactionInformation;
  }
}
