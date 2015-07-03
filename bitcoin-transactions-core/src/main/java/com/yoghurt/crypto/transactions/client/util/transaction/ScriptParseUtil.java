package com.yoghurt.crypto.transactions.client.util.transaction;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
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
    if (isCoinbase) {
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
      throw new IllegalStateException("More bytes than advertised were consumed in the script. (advertised:" + length + ", actual:" + (pointer - initialPointer) + ")");
    }

    return pointer;
  }

  private static int parseOpcode(final int initialPointer, final ScriptEntity script, final byte[] bytes) {
    int pointer = initialPointer;

    GWT.log("Parsing opcode at pointer: " + pointer);
    final int opcode = bytes[pointer] & 0xFF;

    if (ScriptOperationUtil.isDataPushOperation(opcode)) {
      pointer = parsePushData(pointer, opcode, script, bytes);
    } else {
      pointer++;
      script.addInstruction(new ScriptPart(ScriptOperationUtil.getOperation(opcode)));
    }

    return pointer;
  }

  private static int parsePushData(final int initialPointer, final int opcode, final ScriptEntity script, final byte[] bytes) {
    int pointer = initialPointer;

    GWT.log("Parsing pushdata.. " + opcode + " > " + ScriptOperationUtil.getOperation(opcode).name());

    // Simple 1-byte pushdata notation.
    if (opcode <= 75) {
      pointer++;
      script.addInstruction(new ScriptPart(Operation.OP_PUSHDATA, ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + opcode)));
    } else {
      // Switch over remaining options; OP_PUSHDATA1, 2, 4
      switch (opcode) {
      case 76: // OP_PUSHDATA1
        GWT.log("Parsing pushdata1 size at pointer: " + (pointer + 1));
        final int size = bytes[pointer = pointer + 1] & 0xFF;

        pointer++;
        final byte[] payload = ArrayUtil.arrayCopy(bytes, pointer, pointer = pointer + size);
        script.addInstruction(new ScriptPart(Operation.OP_PUSHDATA1, payload));
        break;
      case 77:
      case 78:
        // TODO Support OP_PUSHDATA2 and 4
        GWT.log("Unsupported data push operation: " + opcode + " (" + Str.toString(Hex.encode(new byte[] { (byte) (opcode & 0xFF) })) + ")");
        throw new UnsupportedOperationException();
      default:
        throw new IllegalStateException("Unreachable code reached. (..)");
      }
    }

    return pointer;
  }
}
