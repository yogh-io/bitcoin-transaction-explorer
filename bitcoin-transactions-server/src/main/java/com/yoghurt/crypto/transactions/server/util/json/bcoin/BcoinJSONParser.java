package com.yoghurt.crypto.transactions.server.util.json.bcoin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

import com.yoghurt.crypto.transactions.server.util.ArrayUtil;
import com.yoghurt.crypto.transactions.server.util.NumberEncodeUtil;
import com.yoghurt.crypto.transactions.server.util.json.JsonParser;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.OutpointInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;

public final class BcoinJSONParser {
  private BcoinJSONParser() {
  }

  public static String parseChainTip(InputStream jsonData) throws JsonProcessingException, IOException {
    final JsonNode tree = JsonParser.readTree(jsonData);

    return tree.get("chain").get("tip").getTextValue();
  }

  public static BlockInformation getBlockInformation(InputStream jsonData) throws JsonProcessingException, IOException, DecoderException {
    final JsonNode tree = JsonParser.readTree(jsonData);

    // Create a builder to assemble the block headers
    final StringBuilder builder = new StringBuilder();

    // Version
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(tree.get("version").getLongValue())));

    // PrevBlock
    byte[] prevBlockHash = Hex.decodeHex(tree.get("prevBlock").getTextValue().toCharArray());
    ArrayUtil.reverse(prevBlockHash);
    builder.append(Hex.encodeHex(prevBlockHash));

    // Merkle root
    byte[] merkleRoot = Hex.decodeHex(tree.get("merkleRoot").getTextValue().toCharArray());
    ArrayUtil.reverse(merkleRoot);
    builder.append(Hex.encodeHex(merkleRoot));

    // Timestamp
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(tree.get("ts").getLongValue())));

    // Bits
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(tree.get("bits").getLongValue())));

    // Nonce
    builder.append(Hex.encodeHex(NumberEncodeUtil.encodeUint32(tree.get("nonce").getLongValue())));

    // Create a BlockInformation object to store the block information in
    final BlockInformation blockInformation = new BlockInformation();

    // Set the raw block headers
    blockInformation.setRawBlockHeaders(builder.toString());

    // Set the height
    blockInformation.setHeight(tree.get("height").getIntValue());

    // Missing: next block
    blockInformation.setNextBlockHash("");

    // Missing: confirmations

    // Set the number of transactions
    blockInformation.setNumTransactions(tree.get("txs").size());
    // blockInformation.setNumTransactions(tree.get("totalTX").getIntValue());

    // Missing: block size

    // Missing: coinbase tx
    final TransactionInformation ti = new TransactionInformation();
    ti.setRawHex(tree.get("txs").get(0).get("hash").getTextValue());
    blockInformation.setCoinbaseInformation(ti);

    return blockInformation;
  }

  public static AddressInformation getAddressInformation(String address, InputStream jsonData)
      throws JsonProcessingException, IOException, DecoderException {
    final JsonNode tree = JsonParser.readTree(jsonData);

    final ArrayList<OutpointInformation> outpoints = new ArrayList<>();

    for (final JsonNode tx : tree) {
      final String txId = tx.get("hash").getTextValue();

      int i = 0;
      for (final JsonNode vout : tx.get("outputs")) {
        final String voutAddress = vout.get("address").getTextValue();
        if (!address.equals(voutAddress)) {
          i++;
          continue;
        }

        final OutpointInformation outpoint = new OutpointInformation();

        outpoint.setIndex(i);
        outpoint.setReferenceTransaction(txId);
        outpoint.setTransactionValue((long) (vout.get("value").asDouble() * 100000000d));

        // Not actually needed.
        // final JsonNode voutScriptPubKey = vout.get("script");
        // final byte[] script =
        // Hex.decodeHex(voutScriptPubKey.get("hex").getTextValue().toCharArray());
        // outpoint.setOutputScript(script);

        outpoints.add(outpoint);
        i++;
      }

    }

    final AddressInformation ai = new AddressInformation();
    ai.setOutpoints(outpoints);
    return ai;
  }
}
