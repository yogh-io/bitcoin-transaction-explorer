package com.yoghurt.crypto.transactions.client.util.transaction;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;

public class TransactionUtil {
  protected static final int TRANSACTION_VERSION_FIELD_SIZE = 4;
  protected static final int TRANSACTION_INPUT_OUTPOINT_SIZE = 32;
  protected static final int TRANSACTION_SEQUENCE_SIZE = 4;
  protected static final int TRANSACTION_OUTPOINT_INDEX_SIZE = 4;
  protected static final int TRANSACTION_OUTPUT_VALUE_SIZE = 8;
  protected static final int TRANSACTION_LOCK_TIME_SIZE = 4;

  public static int getTransactionSize(Stream<Entry<TransactionPartType, byte[]>> trans) {
    return trans.mapToInt(e -> e.getValue().length).sum();
  }

  public static byte[] getStrippedTransactionBytes(RawTransactionContainer trans) {
    // Oh god
    return unBox(getStrippedTransaction(trans).map(e -> e.getValue()).flatMap(x -> Arrays.stream(box(x))).toArray(n -> new Byte[n]));
  }

  public static Stream<Entry<TransactionPartType, byte[]>> getStrippedTransaction(RawTransactionContainer trans) {
    return trans.stream().filter(stripWitness());
  }

  public static Predicate<Entry<TransactionPartType, byte[]>> stripWitness() {
    return p -> p.getKey().isWitnessPartType();
  }

  private static Byte[] box(final byte[] arr) {
    final Byte[] res = new Byte[arr.length];
    for (int i = 0; i < arr.length; i++) {
      res[i] = arr[i];
    }
    return res;
  }

  private static byte[] unBox(final Byte[] arr) {
    final byte[] res = new byte[arr.length];
    for (int i = 0; i < arr.length; i++) {
      res[i] = arr[i];
    }
    return res;
  }

  public static int getWeight(RawTransactionContainer trans) {
    return getBaseSize(trans) * 3 + getTotalSize(trans);
  }

  public static int getVirtualSize(RawTransactionContainer trans) {
    return (int) Math.ceil(getWeight(trans) / 4D);
  }

  public static int getBaseSize(RawTransactionContainer trans) {
    return getTransactionSize(getStrippedTransaction(trans));
  }

  public static int getTotalSize(RawTransactionContainer trans) {
    return getTransactionSize(trans.stream());
  }
}
