package com.yoghurt.crypto.transactions.client.util.block;

import java.util.Date;

import com.yoghurt.crypto.transactions.client.domain.Block;
import com.yoghurt.crypto.transactions.client.util.ArrayUtil;
import com.yoghurt.crypto.transactions.client.util.NumberParseUtil;
import com.yoghurt.crypto.transactions.client.util.transaction.ComputeUtil;

public final class BlockParseUtil extends BlockUtil {
  private static final int MS_TO_S = 1000;

  public static Block parseBlockBytes(final byte[] bytes) {
    return parseBlockBytes(bytes, new Block());
  }

  public static Block parseBlockBytes(final byte[] bytes, final Block b) {
    return parseBlockBytes(bytes, b, 0);
  }

  private static Block parseBlockBytes(final byte[] bytes, final Block block, final int initialPointer) {
    int pointer = initialPointer;

    // Parse the version bytes
    pointer = parseVersion(block, pointer, bytes);

    // Parse the previous block hash
    pointer = parsePreviousBlockHash(block, pointer, bytes);

    // Parse the merkle root
    pointer = parseMerkleRoot(block, pointer, bytes);

    // Parse the timestamp field
    pointer = parseTimestamp(block, pointer, bytes);

    // Parse the bits field
    pointer = parseBits(block, pointer, bytes);

    // Parse the nonce
    pointer = parseNonce(block, pointer, bytes);

    // Compute the block hash
    computeBlockHash(block, initialPointer, pointer, bytes);

    return block;
  }

  private static void computeBlockHash(final Block block, final int initialPointer, final int pointer, final byte[] bytes) {
    final byte[] blockBytes = ArrayUtil.arrayCopy(bytes, initialPointer, pointer);

    // Create SHA256 digest and feed it the tx bytes
    final byte[] blockHash = ComputeUtil.computeDoubleSHA256(blockBytes);

    // Convert to LE
    ArrayUtil.reverse(blockHash);

    // Set the transaction tx
    block.setBlockHash(blockHash);
  }

  private static int parseVersion(final Block block, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;
    block.setVersion(NumberParseUtil.parseUint32(ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + BLOCK_VERSION_FIELD_SIZE)));
    return pointer;
  }

  private static int parsePreviousBlockHash(final Block block, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;

    final byte[] prevBlockBytes = ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + BLOCK_PREVIOUS_BLOCK_HASH_SIZE);
    ArrayUtil.reverse(prevBlockBytes);

    block.setPreviousBlockHash(prevBlockBytes);
    return pointer;
  }

  private static int parseMerkleRoot(final Block block, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;

    final byte[] merkleRootBytes = ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + BLOCK_MERKLE_ROOT_SIZE);
    ArrayUtil.reverse(merkleRootBytes);

    block.setMerkleRoot(merkleRootBytes);
    return pointer;
  }

  private static int parseBits(final Block block, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;

    final byte[] bitsBytes = ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + BLOCK_BITS_FIELD_SIZE);
    ArrayUtil.reverse(bitsBytes);

    block.setBits(bitsBytes);
    return pointer;
  }

  private static int parseTimestamp(final Block block, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;
    block.setTimestamp(new Date(NumberParseUtil.parseUint32(ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + BLOCK_TIMESTAMP_FIELD_SIZE)) * MS_TO_S));
    return pointer;
  }

  private static int parseNonce(final Block block, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;
    block.setNonce(NumberParseUtil.parseUint32(ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + BLOCK_NONCE_FIELD_SIZE)));
    return pointer;
  }
}
