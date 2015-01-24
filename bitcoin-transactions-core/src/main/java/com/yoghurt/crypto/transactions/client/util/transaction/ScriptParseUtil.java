package com.yoghurt.crypto.transactions.client.util.transaction;

import com.yoghurt.crypto.transactions.shared.domain.Operation;
import com.yoghurt.crypto.transactions.shared.domain.ScriptEntity;
import com.yoghurt.crypto.transactions.shared.domain.ScriptPart;
import com.yoghurt.crypto.transactions.shared.domain.VariableLengthInteger;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;

public final class ScriptParseUtil {
  private ScriptParseUtil() {}

  public static int parseScript(final ScriptEntity entity, final int initialPointer, final byte[] bytes, final boolean isCoinbase) {
    int pointer = initialPointer;

    // Parse the number of bytes in the script
    pointer = parseScriptSize(entity, pointer, bytes);

    // Parse the actual script bytes
    if(isCoinbase) {
      pointer = parseCoinbaseScriptBytes(entity, pointer, bytes, entity.getScriptSize().getValue());
    } else {
      pointer = parseScriptBytes(entity, pointer, bytes, entity.getScriptSize().getValue());
    }

    return pointer;
  }

  public static ScriptEntity parseScript(final byte[] scriptBytes) {
    final ScriptEntity entity = new ScriptEntity();

    parseScriptBytes(entity, 0, scriptBytes, scriptBytes.length);

    return entity;
  }

  private static int parseCoinbaseScriptBytes(final ScriptEntity entity, final int initialPointer, final byte[] bytes, final long value) {
    int pointer = initialPointer;

    entity.addInstruction(new ScriptPart(null, ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + (int) entity.getScriptSize().getValue())));

    return pointer;
  }

  private static int parseScriptSize(final ScriptEntity scriptEntity, final int pointer, final byte[] bytes) {
    final VariableLengthInteger variableInteger = new VariableLengthInteger(bytes, pointer);
    scriptEntity.setScriptSize(variableInteger);
    return pointer + variableInteger.getByteSize();
  }

  private static int parseScriptBytes(final ScriptEntity scriptEntity, final int initialPointer, final byte[] bytes, final long length) {
    int pointer = initialPointer;

    while (pointer < initialPointer + length) {
      pointer = parseOpcode(pointer, scriptEntity, bytes);
    }

    if (pointer != initialPointer + length) {
      throw new IllegalStateException("More bytes than advertised were consumed in the script. (advertised:" + length + ", actual:"
          + (pointer - initialPointer) + ")");
    }

    return pointer;
  }

  private static int parseOpcode(final int initialPointer, final ScriptEntity script, final byte[] bytes) {
    int pointer = initialPointer;

    final int opcode = bytes[pointer] & 0xFF;

    if(ScriptOperationUtil.isDataPushOperation(opcode)) {
      // TODO Implement OP_PUSHDATA1/2/4 and OP_2-OP_16
      if(opcode > 75) {
        throw new UnsupportedOperationException();
      }

      pointer = pointer + 1;

      script.addInstruction(new ScriptPart(Operation.OP_PUSHDATA, ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + opcode)));
    } else {
      script.addInstruction(new ScriptPart(ScriptOperationUtil.getOperation(opcode)));
      pointer = pointer + 1;
    }

    return pointer;
  }
}
