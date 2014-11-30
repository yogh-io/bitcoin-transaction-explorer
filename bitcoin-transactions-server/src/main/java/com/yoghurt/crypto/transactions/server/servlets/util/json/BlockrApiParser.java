package com.yoghurt.crypto.transactions.server.servlets.util.json;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.codec.binary.Hex;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionState;
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

    info.setBlockHeight(data.get("block").getIntValue());
    info.setState(data.get("is_unconfirmed").getBooleanValue() ? TransactionState.UNCONFIRMED : TransactionState.CONFIRMED);
    info.setTime(DATETIME_FORMATTER.parse(data.get("time_utc").getTextValue()));
    info.setConfirmations(data.get("confirmations").getIntValue());

    return info;
  }

  public static String getRawBlockHex(final InputStream jsonData) throws JsonProcessingException, IOException, ParseException {
    final JsonNode tree = JsonParser.mapper.readTree(jsonData);

    checkSuccess(tree);

    final JsonNode data = tree.get("data");

    final StringBuilder builder = new StringBuilder();

    // Version
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(data.get("version").getLongValue())));

    // Prev block hash
    builder.append(data.get("previousblockhash").getTextValue());

    // Merkle root
    builder.append(data.get("merkleroot").getTextValue());

    // Timestamp
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(data.get("time").getLongValue())));

    // Bits
    builder.append(data.get("bits").getTextValue());

    // Nonce
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(data.get("nonce").getLongValue())));

    assert builder.length() == BLOCK_LENGTH_HEX;

    return builder.toString();
  }

  private static void checkSuccess(final JsonNode tree) {
    if(!STATUS_SUCCESS.equals(tree.get("status").getTextValue())) {
      throw new IllegalStateException("JSON response does not indicate success.");
    }
  }
}
