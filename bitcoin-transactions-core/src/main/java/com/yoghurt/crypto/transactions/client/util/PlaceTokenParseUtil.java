package com.yoghurt.crypto.transactions.client.util;

import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.place.ApplicationPlace;
import com.yoghurt.crypto.transactions.client.place.BlockPlace;
import com.yoghurt.crypto.transactions.client.place.BlockPlace.BlockDataType;
import com.yoghurt.crypto.transactions.client.place.ConfigPlace;
import com.yoghurt.crypto.transactions.client.place.MinePlace;
import com.yoghurt.crypto.transactions.client.place.MinePlace.MineDataType;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace.TransactionDataType;
import com.yoghurt.crypto.transactions.client.util.block.BlockParseUtil;
import com.yoghurt.crypto.transactions.client.util.transaction.TransactionParseUtil;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;

public final class PlaceTokenParseUtil {
  private static final String MINE_TOKEN = "mine";
  private static final String LAST_BLOCK_TOKEN = "last";
  private static final String CONFIG_TOKEN = "config";

  // Bunch of zeroes every valid block starts with
  private static final String BLOCK_TOKEN_START = "0000000000";

  // Hash length (256 bit) if encoded into hex
  private static final int HASH_LENGTH = 64;

  // Allows for block heights up to 9999999, will last until the year 2190 or so, I guess we're good
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

    if(CONFIG_TOKEN.equals(cleanToken)) {
      return new ConfigPlace();
    }

    // Check if the token is exactly equal to the length of a hash, meaning this is probably a transaction or block hash
    if (cleanToken.length() == HASH_LENGTH) {
      // If it starts with a bunch of zeroes, it's probably a block, otherwise it may be a transaction
      if (cleanToken.startsWith(BLOCK_TOKEN_START)) {
        return new BlockPlace(BlockDataType.ID, cleanToken);
      } else {
        return new TransactionPlace(TransactionDataType.ID, cleanToken);
      }
    }

    // If the token is longer than the hash size, this could be a raw transaction or a raw block, so try and parse it
    if(cleanToken.length() > HASH_LENGTH) {
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
        }catch (final Exception e2) {
          // Eat, this is neither a raw transaction or block
        }
      }
    }

    // Check if the token is a number, probably block height if it is
    if(cleanToken.length() <= MAX_BLOCK_HEIGHT_NUMBER_LENGTH) {
      try {
        final int possibleBlockHeight = Integer.parseInt(cleanToken);
        return new BlockPlace(BlockDataType.HEIGHT, possibleBlockHeight);
      } catch (final NumberFormatException e) {
        // Eat, this is probably not a number ;)
      }
    }

    // Nope, don't know what to do with this, return null
    return null;
  }
}
