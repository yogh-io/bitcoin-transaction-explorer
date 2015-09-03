package com.yoghurt.crypto.transactions.client.util;

import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.place.AddressPlace;
import com.yoghurt.crypto.transactions.client.place.AddressPlace.AddressDataType;
import com.yoghurt.crypto.transactions.client.place.ApplicationPlace;
import com.yoghurt.crypto.transactions.client.place.BlockPlace;
import com.yoghurt.crypto.transactions.client.place.BlockPlace.BlockDataType;
import com.yoghurt.crypto.transactions.client.place.ConfigPlace;
import com.yoghurt.crypto.transactions.client.place.ContributePlace;
import com.yoghurt.crypto.transactions.client.place.MinePlace;
import com.yoghurt.crypto.transactions.client.place.MinePlace.MineDataType;
import com.yoghurt.crypto.transactions.client.place.RPCResponsePlace;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace.TransactionDataType;
import com.yoghurt.crypto.transactions.client.util.address.AddressParseUtil;
import com.yoghurt.crypto.transactions.client.util.block.BlockParseUtil;
import com.yoghurt.crypto.transactions.client.util.transaction.TransactionParseUtil;
import com.yoghurt.crypto.transactions.shared.domain.Base58CheckContents;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.JSONRPCMethod;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;

public final class PlaceTokenParseUtil {
  private static final String MINE_TOKEN = "mine";
  private static final String LAST_BLOCK_TOKEN = "last";
  private static final String CONFIG_TOKEN = "config";
  private static final String CONTRIBUTE_TOKEN = "contribute";
  private static final String NBSP = " ";

  // Bunch of zeroes every valid block starts with
  private static final String BLOCK_TOKEN_START = "0000000000";

  // Hash length (256 bit) if encoded into hex
  private static final int HASH_LENGTH = 64;

  // Allows for block heights up to 9999999, will last until the year 2190 or
  // so, I guess we're good
  private static final int MAX_BLOCK_HEIGHT_NUMBER_LENGTH = 7;

  private PlaceTokenParseUtil() {}

  public static ApplicationPlace parseToken(final String token) {
    // Clean up the token first
    final String cleanToken = token.replace(" ", "");

    // Check for keywords
    if (MINE_TOKEN.equals(cleanToken)) {
      return new MinePlace(MineDataType.LAST);
    }
    if (LAST_BLOCK_TOKEN.equals(cleanToken)) {
      return new BlockPlace(BlockDataType.LAST);
    }

    if (CONFIG_TOKEN.equals(cleanToken)) {
      return new ConfigPlace();
    }

    if (CONTRIBUTE_TOKEN.equals(cleanToken)) {
      return new ContributePlace();
    }

    final String[] splitToken = token.split(NBSP);
    if (JSONRPCMethod.fromName(splitToken[0]) != null) {
      return new RPCResponsePlace(splitToken);
    }

    // Check if the token can be base58 decoded, and if so, if the result looks
    // like an address (20 byte payload)
    try {
      final Base58CheckContents address = AddressParseUtil.parseAddress(cleanToken);
      if (address.getPayload().length == 20) {
        return new AddressPlace(AddressDataType.ID, cleanToken);
      }
    } catch (final Exception e) {
      // Guess not.
    }

    // Check if the token is exactly equal to the length of a hash (32 bytes), meaning this
    // is probably a transaction or block hash
    if (cleanToken.length() == HASH_LENGTH) {
      // If it starts with a bunch of zeroes, it's probably a block, otherwise
      // it may be a transaction
      if (cleanToken.startsWith(BLOCK_TOKEN_START)) {
        return new BlockPlace(BlockDataType.ID, cleanToken);
      } else {
        return new TransactionPlace(TransactionDataType.ID, cleanToken);
      }
    }

    // If the token is longer than the hash size, this could be a raw
    // transaction or a raw block, so try and parse it
    if (cleanToken.length() > HASH_LENGTH) {
      try {
        // If this works out, it's a transaction!
        final Transaction t = new Transaction();
        TransactionParseUtil.parseTransactionBytes(Hex.decode(cleanToken), t);
        return new TransactionPlace(TransactionDataType.RAW, cleanToken);
      } catch (final Exception e1) {
        try {
          // If this works out, it's a block!
          final Block b = new Block();
          BlockParseUtil.parseBlockBytes(Hex.decode(cleanToken), b);
          return new BlockPlace(BlockDataType.RAW, cleanToken);
        } catch (final Exception e2) {
          // Eat, this is neither a raw transaction or block
        }
      }
    }

    // Check if the token is a number, probably block height if it is
    if (cleanToken.length() <= MAX_BLOCK_HEIGHT_NUMBER_LENGTH) {
      try {
        final int possibleBlockHeight = Integer.parseInt(cleanToken);

        // Can't be less than 0 though..
        if (possibleBlockHeight >= 0) {
          return new BlockPlace(BlockDataType.HEIGHT, possibleBlockHeight);
        }
      } catch (final NumberFormatException e) {
        // Eat, this is probably not a number ;)
      }
    }

    // Nope, don't know what to do with this, return null
    return null;
  }
}
