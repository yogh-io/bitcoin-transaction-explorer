package com.yoghurt.crypto.transactions.client.util;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.domain.transaction.ScriptEntity;
import com.yoghurt.crypto.transactions.client.domain.transaction.SignatureScript;
import com.yoghurt.crypto.transactions.client.domain.transaction.Transaction;
import com.yoghurt.crypto.transactions.client.domain.transaction.TransactionInput;
import com.yoghurt.crypto.transactions.client.domain.transaction.TransactionOutPoint;
import com.yoghurt.crypto.transactions.client.domain.transaction.TransactionOutput;

public final class TransactionParseUtil extends TransactionUtil {
  private TransactionParseUtil() {}


  public static Transaction parseTransactionHex(final String hex) {
    return parseTransactionHex(hex, new Transaction());
  }

  public static Transaction parseTransactionHex(final String hex, final Transaction transaction) {
    return parseTransactionBytes(Hex.decode(hex.getBytes()), transaction);
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

    // Parse the transaction inputs
    pointer = parseTransactionInputs(transaction, pointer, bytes);

    // Parse the transaction output size
    pointer = parseTransactionOutputSize(transaction, pointer, bytes);

    // Parse the transaction outputs
    pointer = parseTransactionOutputs(transaction, pointer, bytes);

    // Parse the lock time
    pointer = parseLockTime(transaction, pointer, bytes);

    // Verify if the byte array size is equal to the pointer
    if(pointer != bytes.length) {
      GWT.log(pointer + " > " + bytes.length);
      throw new IllegalStateException("Raw transaction bytes not fully consumed");
    }

    return transaction;
  }

  private static int parseTransactionInputs(final Transaction transaction, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;

    final long numInputs = transaction.getInputSize().getValue();
    final ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>((int) numInputs);

    // Stick it all in the transaction
    transaction.setInputs(inputs);

    // Iterate over the number of inputs in the transaction
    for (long i = 0; i < numInputs; i++) {
      // Create an empty transaction input
      final TransactionInput input = new TransactionInput();

      // Parse the tx out point to the previous transaction
      pointer = parseTransactionOutPoint(input, pointer, bytes);

      // Parse the script length
      pointer = parseScriptSize(input, pointer, bytes);

      // Parse the signature script
      pointer = parseScriptBytes(input, pointer, bytes);

      // Parse the sequence bytes
      pointer = parseSequence(input, pointer, bytes);

      // Add the complete input to the list
      inputs.add(input);
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
    for (long i = 0; i < numOutputs; i++) {
      // Create an empty transaction output
      final TransactionOutput output = new TransactionOutput();

      // Parse the transaction value
      output.setTransactionValue(NumberParseUtil.parseLong(arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_OUTPUT_VALUE_SIZE)));

      // Parse the script length
      pointer = parseScriptSize(output, pointer, bytes);

      // Parse the script bytes
      pointer = parseScriptBytes(output, pointer, bytes);

      // Add the complete output to the list
      outputs.add(output);
    }

    return pointer;
  }

  private static int parseTransactionOutPoint(final TransactionInput input, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;

    final TransactionOutPoint outPoint = new TransactionOutPoint();
    outPoint.setReferenceTransaction(arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_INPUT_OUTPOINT_SIZE));
    outPoint.setIndex(NumberParseUtil.parseUint32(arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_OUTPOINT_INDEX_SIZE)));

    input.setOutPoint(outPoint);

    return pointer;
  }

  private static int parseTransactionInputSize(final Transaction transaction, final int pointer, final byte[] bytes) {
    final VariableInteger variableInteger = new VariableInteger(bytes, pointer);
    transaction.setInputSize(variableInteger);
    return pointer + variableInteger.getByteSize();
  }

  private static int parseTransactionOutputSize(final Transaction transaction, final int pointer, final byte[] bytes) {
    final VariableInteger variableInteger = new VariableInteger(bytes, pointer);
    transaction.setOutputSize(variableInteger);
    return pointer + variableInteger.getByteSize();
  }

  private static int parseScriptSize(final ScriptEntity scriptEntity, final int pointer, final byte[] bytes) {
    final VariableInteger variableInteger = new VariableInteger(bytes, pointer);
    scriptEntity.setScriptSize(variableInteger);
    return pointer + variableInteger.getByteSize();
  }

  private static int parseScriptBytes(final ScriptEntity scriptEntity, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;

    final SignatureScript signatureScript = new SignatureScript();
    signatureScript.setScriptBytes(arrayCopy(bytes, pointer, pointer = pointer + (int) scriptEntity.getScriptSize().getValue()));

    scriptEntity.setSignatureScript(signatureScript);

    return pointer;
  }

  private static int parseSequence(final TransactionInput input, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;
    input.setTransactionSequence(NumberParseUtil.parseUint32(arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_SEQUENCE_SIZE)));
    return pointer;
  }

  private static int parseVersion(final Transaction transaction, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;
    transaction.setVersion(NumberParseUtil.parseUint32(arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_VERSION_FIELD_SIZE)));
    return pointer;
  }

  private static int parseLockTime(final Transaction transaction, final int initialPointer, final byte[] bytes) {
    int pointer = initialPointer;
    transaction.setLockTime(NumberParseUtil.parseUint32(arrayCopy(bytes, pointer, pointer = pointer + TRANSACTION_LOCK_TIME_SIZE)));
    return pointer;
  }

  @Deprecated
  private static byte[] arrayCopy(final byte[] bytes, final int start, final int end) {
    final byte[] destBytes = new byte[end - start];

    System.arraycopy(bytes, start, destBytes, 0, end - start);

    return destBytes;
  }
}
