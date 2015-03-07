package com.yoghurt.crypto.transactions.server.util.json;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionState;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;
import com.yoghurt.crypto.transactions.shared.util.NumberEncodeUtil;

public final class BlockrApiParser {
  private static final String STATUS_SUCCESS = "success";
  private static final DateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
  private static final int BLOCK_LENGTH_HEX = 160;

  private BlockrApiParser() {}

  public static String getRawTransactionHex(final InputStream jsonData) throws JsonProcessingException, IOException {
    final JsonNode tree = JsonParser.mapper.readTree(jsonData);

    checkSuccess(tree);

    return tree.get("data").get("tx").get("hex").getTextValue();
  }

  public static TransactionInformation getTransactionInformation(final InputStream jsonData) throws JsonProcessingException, IOException, ParseException {
    final JsonNode tree = JsonParser.mapper.readTree(jsonData);

    checkSuccess(tree);

    final TransactionInformation info = new TransactionInformation();

    final JsonNode data = tree.get("data");

    //    info.setBlockHeight(data.get("block").getIntValue());
    info.setState(data.get("is_unconfirmed").getBooleanValue() ? TransactionState.UNCONFIRMED : TransactionState.CONFIRMED);
    info.setTime(DATETIME_FORMATTER.parse(data.get("time_utc").getTextValue()));
    info.setConfirmations(data.get("confirmations").getIntValue());

    return info;
  }

  public static String getRawBlockHex(final InputStream jsonData) throws JsonProcessingException, IOException, ParseException, DecoderException {
    final JsonNode tree = JsonParser.mapper.readTree(jsonData);

    checkSuccess(tree);

    final JsonNode data = tree.get("data");

    final StringBuilder builder = new StringBuilder();

    // Version
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(data.get("version").getLongValue())));

    // Prev block hash (LE<>BE)
    final byte[] prevBlockHash = Hex.decodeHex(data.get("previousblockhash").getTextValue().toCharArray());
    ArrayUtil.reverse(prevBlockHash);
    builder.append(Hex.encodeHex(prevBlockHash));

    // Merkle root (LE<>BE)
    final byte[] merkleroot = Hex.decodeHex(data.get("merkleroot").getTextValue().toCharArray());
    ArrayUtil.reverse(merkleroot);
    builder.append(Hex.encodeHex(merkleroot));

    // Timestamp
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(data.get("time").getLongValue())));

    // Bits (LE<>BE)
    final byte[] bits = Hex.decodeHex(data.get("bits").getTextValue().toCharArray());
    ArrayUtil.reverse(bits);
    builder.append(Hex.encodeHex(bits));

    // Nonce
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(data.get("nonce").getLongValue())));

    assert builder.length() == BLOCK_LENGTH_HEX;

    return builder.toString();
  }

  public static String getBlockHash(final InputStream jsonData) throws JsonProcessingException, IOException {
    final JsonNode tree = JsonParser.mapper.readTree(jsonData);

    checkSuccess(tree);

    return tree.get("data").get("hash").getTextValue();
  }

  public static BlockInformation getBlockInformation(final InputStream jsonData) throws JsonProcessingException, IOException, ParseException, DecoderException {
    final JsonNode tree = JsonParser.mapper.readTree(jsonData);

    checkSuccess(tree);

    final JsonNode data = tree.get("data");

    final BlockInformation info = new BlockInformation();

    info.setHeight(data.get("nb").getIntValue());
    info.setNumConfirmations(data.get("confirmations").getIntValue());
    info.setNumTransactions(data.get("nb_txs").getIntValue());
    info.setNextBlockHash(data.get("next_block_hash").getTextValue());
    info.setSize(data.get("size").asInt());

    return info;

  }

  private static void checkSuccess(final JsonNode tree) {
    if(!STATUS_SUCCESS.equals(tree.get("status").getTextValue())) {
      throw new IllegalStateException("JSON response does not indicate success.");
    }
  }
}
