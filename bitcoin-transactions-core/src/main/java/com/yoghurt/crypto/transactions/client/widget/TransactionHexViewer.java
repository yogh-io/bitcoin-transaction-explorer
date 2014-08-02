package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.domain.transaction.RawTransactionContainer;
import com.yoghurt.crypto.transactions.client.domain.transaction.RawTransactionPart;
import com.yoghurt.crypto.transactions.client.domain.transaction.script.Operation;
import com.yoghurt.crypto.transactions.client.util.Color;
import com.yoghurt.crypto.transactions.client.util.ColorBuilder;
import com.yoghurt.crypto.transactions.client.util.NumberParseUtil;
import com.yoghurt.crypto.transactions.client.util.ScriptOperationUtil;
import com.yoghurt.crypto.transactions.client.util.VariableLengthInteger;

public class TransactionHexViewer extends HexViewer<RawTransactionPart> {
  public void setTransaction(final RawTransactionContainer rawTransaction) {
    clear();

    for (final RawTransactionPart part : rawTransaction) {
      addFields(part);
    }
  }

  @Override
  protected Color getColorForValue(final RawTransactionPart value) {
    final Color color;

    switch (value.getType()) {
    case INPUT_OUTPOINT_HASH:
      color = ColorBuilder.interpret("mediumslateblue");
      break;
    case INPUT_OUTPOINT_INDEX:
      color = ColorBuilder.interpret("lightblue");
      break;
    case OUTPUT_SCRIPT_LENGTH:
    case INPUT_SCRIPT_LENGTH:
      color = ColorBuilder.interpret("mediumvioletred");
      break;
    case INPUT_SEQUENCE:
      color = ColorBuilder.interpret("lightgreen");
      break;
    case INPUT_SIZE:
    case OUTPUT_SIZE:
      color = ColorBuilder.interpret("pink");
      break;
    case OUTPUT_VALUE:
      color = ColorBuilder.interpret("gold");
      break;
    case OPCODE:
      color = ColorBuilder.interpret("red");
      break;
    case OPCODE_PUSH_DATA:
      color = ColorBuilder.interpret("green");
      break;
    case LOCK_TIME:
    case VERSION:
    default:
      color = ColorBuilder.interpret("grey");
      break;
    }

    return color;
  }

  @Override
  protected Widget createContextPopupContent(final RawTransactionPart part) {
    final Label label = new Label(getContextForValue(part));

    label.getElement().getStyle().setPadding(10, Unit.PX);

    return label;
  }

  protected String getContextForValue(final RawTransactionPart value) {
    switch (value.getType()) {
    case INPUT_OUTPOINT_HASH:
      return "Previous transaction hash";
    case INPUT_OUTPOINT_INDEX:
      return "Previous output index: " + new VariableLengthInteger(value.getBytes()).getValue();
    case OUTPUT_SCRIPT_LENGTH:
      return "Output script length: " + new VariableLengthInteger(value.getBytes()).getValue();
    case INPUT_SCRIPT_LENGTH:
      return "Input script length: " + new VariableLengthInteger(value.getBytes()).getValue();
    case INPUT_SEQUENCE:
      return "Input sequence number";
    case INPUT_SIZE:
      return "Number of inputs: " + new VariableLengthInteger(value.getBytes()).getValue();
    case OUTPUT_SIZE:
      return "Number of outputs: " + new VariableLengthInteger(value.getBytes()).getValue();
    case OUTPUT_VALUE:
      return "Output value: " + NumberParseUtil.parseLong(value.getBytes()) / 100000000d + " BTC";
    case OPCODE:
      final Operation operation = ScriptOperationUtil.getOperation(value.getBytes()[0] & 0xFF);
      if(ScriptOperationUtil.isDataPushOperation(operation)) {
        return "Operation: " + operation.name() + " (" + (value.getBytes()[0] & 0xFF) + " bytes)";
      } else {
        return "Operation: " + operation.name();
      }
    case OPCODE_PUSH_DATA:
      return "Data";
    case LOCK_TIME:
      return "Transaction lock time";
    case VERSION:
      return "Transaction version";
    default:
      return "Unknown transaction part";
    }
  }

  @Override
  protected byte[] getBytesForvalue(final RawTransactionPart value) {
    return value.getBytes();
  }
}
