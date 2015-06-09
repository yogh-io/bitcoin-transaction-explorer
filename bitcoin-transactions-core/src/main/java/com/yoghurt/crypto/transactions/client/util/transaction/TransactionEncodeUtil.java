package com.yoghurt.crypto.transactions.client.util.transaction;

import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.ScriptType;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;
import com.yoghurt.crypto.transactions.shared.domain.VariableLengthInteger;
import com.yoghurt.crypto.transactions.shared.util.NumberEncodeUtil;

public final class TransactionEncodeUtil extends TransactionUtil {
  private TransactionEncodeUtil() {}

  public static RawTransactionContainer encodeTransaction(final Transaction transaction) {
    return encodeTransaction(transaction, new RawTransactionContainer());
  }

  public static RawTransactionContainer encodeTransaction(final Transaction transaction, final RawTransactionContainer container) {
    // Encode the version
    container.add(TransactionPartType.VERSION, encodeVersion(transaction));

    // Encode the input length
    container.add(TransactionPartType.INPUT_SIZE, encodeNumInputs(transaction));

    // Encode the inputs
    encodeTransactionInputs(transaction, container);

    // Encode the output length
    container.add(TransactionPartType.OUTPUT_SIZE, encodeNumOutputs(transaction));

    // Encode the outputs
    encodeTransactionOutputs(transaction, container);

    // Encode the lock time
    container.add(TransactionPartType.LOCK_TIME, encodeLockTime(transaction));

    return container;
  }

  private static void encodeTransactionInputs(final Transaction transaction, final RawTransactionContainer container) {
    for (int i = 0; i < transaction.getInputSize().getValue(); i++) {
      encodeInput(transaction.getInputs().get(i), container);
    }
  }

  private static void encodeTransactionOutputs(final Transaction transaction, final RawTransactionContainer container) {
    for (int i = 0; i < transaction.getOutputs().size(); i++) {
      encodeOutput(transaction.getOutputs().get(i), container);
    }
  }

  private static void encodeInput(final TransactionInput input, final RawTransactionContainer container) {
    // Encode the outpoint hash
    final byte[] outpointHash = input.getOutPoint().getReferenceTransaction();
    container.add(TransactionPartType.INPUT_PREVIOUS_TRANSACTION_HASH, outpointHash);

    // Encode the outpoint index
    final byte[] indexBytes = NumberEncodeUtil.encodeUint32(input.getOutPoint().getIndex());
    container.add(TransactionPartType.INPUT_OUTPOINT_INDEX, indexBytes);

    // Encode the signature length
    final byte[] scriptSizeBytes = encodeVariableInteger(input.getScriptSize());
    container.add(TransactionPartType.INPUT_SCRIPT_LENGTH, scriptSizeBytes);

    // Encode the signature script
    ScriptEncodeUtil.encodeScript(input, container, ScriptType.SCRIPT_SIG);

    // Encode the sequence bytes
    final byte[] sequenceBytes = NumberEncodeUtil.encodeUint32(input.getTransactionSequence());
    container.add(TransactionPartType.INPUT_SEQUENCE, sequenceBytes);
  }

  private static void encodeOutput(final TransactionOutput output, final RawTransactionContainer container) {
    // Encode the output value
    final byte[] outputValueBytes = output.getTransactionValue();
    container.add(TransactionPartType.OUTPUT_VALUE, outputValueBytes);

    // Encode the output script length
    final byte[] scriptSizeBytes = encodeVariableInteger(output.getScriptSize());
    container.add(TransactionPartType.OUTPUT_SCRIPT_LENGTH, scriptSizeBytes);

    // Encode the output script
    ScriptEncodeUtil.encodeScript(output, container, ScriptType.SCRIPT_PUB_KEY);
  }

  private static byte[] encodeLockTime(final Transaction transaction) {
    return NumberEncodeUtil.encodeUint32(transaction.getLockTime());
  }

  private static byte[] encodeNumOutputs(final Transaction transaction) {
    return encodeVariableInteger(transaction.getOutputSize());
  }

  private static byte[] encodeNumInputs(final Transaction transaction) {
    return encodeVariableInteger(transaction.getInputSize());
  }

  private static byte[] encodeVariableInteger(final VariableLengthInteger varInt) {
    return varInt.getBytes();
  }

  private static byte[] encodeVersion(final Transaction t) {
    return NumberEncodeUtil.encodeUint32(t.getVersion());
  }
}
