package com.yoghurt.crypto.transactions.client.util.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.yoghurt.crypto.transactions.client.util.witness.WitnessParseUtil;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutPoint;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;
import com.yoghurt.crypto.transactions.shared.domain.VariableLengthInteger;
import com.yoghurt.crypto.transactions.shared.domain.WitnessEntity;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;
import com.yoghurt.crypto.transactions.shared.util.NumberParseUtil;
import com.yoghurt.crypto.transactions.shared.util.ScriptParseUtil;

public final class TransactionParseUtil extends TransactionUtil {
  private TransactionParseUtil() {
  }

  public static Transaction parseTransactionBytes(final byte[] bytes) {
    return parseTransactionBytes(bytes, new Transaction());
  }

  public static Transaction parseTransactionBytes(final byte[] bytes, final Transaction transaction) {
    return parseTransactionBytes(bytes, transaction, 0);
  }

  public static Transaction parseTransactionBytes(final byte[] bytes, final Transaction transaction, final int initialPointer) {
    int pointer = initialPointer;

    // Parse the version bytes
    pointer = parseVersion(transaction, pointer, bytes);

    // Parse the transaction input size
    pointer = parseTransactionInputSize(transaction, pointer, bytes);

    // If the transaction input size indicates segwit (00 marker), parse the segwit
    // flag
    if (transaction.getInputSize().getValue() == 0) {
      pointer = parseWitnessFlag(transaction, pointer, bytes);

      // Parse the now-pushed-forward transaction input size
      pointer = parseTransactionInputSize(transaction, pointer, bytes);
    }

    // Parse the transaction inputs
    pointer = parseTransactionInputs(transaction, pointer, bytes);

    // Parse the transaction output size
    pointer = parseTransactionOutputSize(transaction, pointer, bytes);

    // Parse the transaction outputs
    pointer = parseTransactionOutputs(transaction, pointer, bytes);

    // If a witness tx, parse the witness script
    if (transaction.isSegregatedWitness()) {
      pointer = parseScriptWitnesses(transaction, pointer, bytes);
    }

    // Parse the lock time
    pointer = parseLockTime(transaction, pointer, bytes);

    // Compute the witness hash
    computeWitnessHash(transaction, initialPointer, pointer, bytes);

    // Compute the transaction hash
    computeTransactionHash(transaction);

    // Verify if the byte array size is equal to the pointer
    if (pointer != bytes.length) {
      throw new IllegalStateException("Raw transaction bytes not fully consumed. " + pointer + " (actual) vs " + bytes.length + " (expected)");
    }

    return transaction;
  }

  private static int parseScriptWitnesses(Transaction transaction, int pointer, byte[] bytes) {
    int witnessSize = (int) transaction.getInputSize().getValue();
    final ArrayList<WitnessEntity> witnesses = new ArrayList<>(witnessSize);
    transaction.setWitnessScripts(witnesses);

    // Iterate over the number of inputs in the transaction
    for (int i = 0; i < witnessSize; i++) {
      // For each witness, create an entity
      final WitnessEntity witness = new WitnessEntity();

      // Add it to the list
      witnesses.add(witness);

      // Parse the witness entities
      pointer = WitnessParseUtil.parseWitness(pointer, witness, bytes);
    }

    return pointer;
  }

  private static int parseWitnessFlag(Transaction transaction, int pointer, byte[] bytes) {
    transaction.setSegregatedWitness(true);

    pointer = parseWitnessProgramVersion(transaction, pointer, bytes);

    return pointer;
  }

  private static int parseWitnessProgramVersion(Transaction transaction, int pointer, byte[] bytes) {
    transaction.setWitnessFlag(bytes[pointer]);
    return pointer + 1;
  }

  private static void computeTransactionHash(Transaction transaction) {
    RawTransactionContainer trans = TransactionEncodeUtil.encodeTransaction(transaction);

    List<byte[]> legacyTransaction = trans.stream().filter(e -> !e.getKey().isWitnessPartType()).map(e -> e.getValue()).collect(Collectors.toList());

    byte[] hash = ComputeUtil.computeDoubleSHA256(legacyTransaction);
    transaction.setTransactionId(hash);
  }

  private static void computeWitnessHash(final Transaction transaction, final int initialPointer, final int pointer, final byte[] bytes) {
    final byte[] txBytes = ArrayUtil.arrayCopy(bytes, initialPointer, pointer);

    // Create SHA256 digest and feed it the tx bytes
    final byte[] txHash = ComputeUtil.computeDoubleSHA256(txBytes);

    // Convert to LE
    ArrayUtil.reverse(txHash);

    // Set the witness hash
    transaction.setWitnessId(txHash);
  }

  private static int parseTransactionInputs(final Transaction transaction, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;

    final long numInputs = transaction.getInputSize().getValue();
    final ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>((int) numInputs);

    // Stick it all in the transaction
    transaction.setInputs(inputs);

    // Iterate over the number of inputs in the transaction
    for (int i = 0; i < numInputs; i++) {
      // Create an empty transaction input
      final TransactionInput input = new TransactionInput();

      // Set the index
      input.setInputIndex(i);

      // Add the input to the list (early, to be able to see from where it went wrong,
      // if it goes wrong)
      inputs.add(input);

      // Parse the tx out point to the previous transaction
      pointer = parseTransactionOutPoint(input, pointer, bytes);

      // Parse the script
      pointer = ScriptParseUtil.parseScript(input, pointer, bytes, transaction.isCoinbase());

      // Parse the sequence bytes
      pointer = parseSequence(input, pointer, bytes);
    }

    return pointer;
  }

  private static int parseTransactionOutputs(final Transaction transaction, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;

    final long numOutputs = transaction.getOutputSize().getValue();
    final ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>((int) numOutputs);

    // Stick it all in the transaction
    transaction.setOutputs(outputs);

    // Iterate over the number of output in the transaction
    for (int i = 0; i < numOutputs; i++) {
      // Create an empty transaction output
      final TransactionOutput output = new TransactionOutput();

      // Set the index
      output.setOutputIndex(i);

      // Add the output to the list (early, to be able to see from where it went
      // wrong, if it goes wrong)
      outputs.add(output);

      // Parse the transaction value
      output.setTransactionValue(NumberParseUtil.parseLong(ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_OUTPUT_VALUE_SIZE)));

      // Parse the script
      pointer = ScriptParseUtil.parseScript(output, pointer, bytes, false);
    }

    return pointer;
  }

  private static int parseTransactionOutPoint(final TransactionInput input, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;

    final TransactionOutPoint outPoint = new TransactionOutPoint();

    final byte[] prevHash = ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_INPUT_OUTPOINT_SIZE);
    outPoint.setReferenceTransaction(prevHash);

    outPoint.setIndex(NumberParseUtil.parseUint32(ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_OUTPOINT_INDEX_SIZE)));

    input.setOutPoint(outPoint);

    return pointer;
  }

  private static int parseTransactionInputSize(final Transaction transaction, final int pointer, final byte[] bytes) {
    final VariableLengthInteger variableInteger = new VariableLengthInteger(bytes, pointer);
    transaction.setInputSize(variableInteger);
    return pointer + variableInteger.getByteSize();
  }

  private static int parseTransactionOutputSize(final Transaction transaction, final int pointer, final byte[] bytes) {
    final VariableLengthInteger variableInteger = new VariableLengthInteger(bytes, pointer);
    transaction.setOutputSize(variableInteger);
    return pointer + variableInteger.getByteSize();
  }

  private static int parseSequence(final TransactionInput input, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;
    input.setTransactionSequence(NumberParseUtil.parseUint32(ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_SEQUENCE_SIZE)));
    return pointer;
  }

  private static int parseVersion(final Transaction transaction, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;
    transaction.setVersion(NumberParseUtil.parseUint32(ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_VERSION_FIELD_SIZE)));
    return pointer;
  }

  private static int parseLockTime(final Transaction transaction, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;
    transaction.setLockTime(NumberParseUtil.parseUint32(ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_LOCK_TIME_SIZE)));
    return pointer;
  }
}
