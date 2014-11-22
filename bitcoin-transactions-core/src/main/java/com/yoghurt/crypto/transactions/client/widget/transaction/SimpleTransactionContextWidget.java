package com.yoghurt.crypto.transactions.client.widget.transaction;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.widget.ContextFieldSet.FieldContextFactory;
import com.yoghurt.crypto.transactions.shared.domain.Operation;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionPart;
import com.yoghurt.crypto.transactions.shared.domain.VariableLengthInteger;
import com.yoghurt.crypto.transactions.shared.util.NumberParseUtil;
import com.yoghurt.crypto.transactions.shared.util.transaction.ScriptOperationUtil;

public class SimpleTransactionContextWidget implements FieldContextFactory<RawTransactionPart> {
  @Override
  public Widget getContextWidget(final RawTransactionPart value) {
    final Label label = new Label(getTextForPart(value));
    label.getElement().getStyle().setPadding(10, Unit.PX);
    return label;
  }

  private String getTextForPart(final RawTransactionPart value) {
    switch (value.getType()) {
    case TRANSACTION_HASH:
      return "Previous transaction hash";
    case INPUT_OUTPOINT_INDEX:
      return "Previous output index: " + new VariableLengthInteger(value.getBytes()).getValue();
    case OUTPUT_SCRIPT_LENGTH:
      return "Output script length: " + new VariableLengthInteger(value.getBytes()).getValue() + " byte";
    case INPUT_SCRIPT_LENGTH:
      return "Input script length: " + new VariableLengthInteger(value.getBytes()).getValue() + " byte";
    case INPUT_SEQUENCE:
      return "Input sequence number";
    case INPUT_SIZE:
      return "Number of inputs: " + new VariableLengthInteger(value.getBytes()).getValue();
    case OUTPUT_SIZE:
      return "Number of outputs: " + new VariableLengthInteger(value.getBytes()).getValue();
    case OUTPUT_VALUE:
      return "Output value: " + NumberParseUtil.parseLong(value.getBytes()) / 100000000d + " BTC";
    case SCRIPT_PUB_KEY_OP_CODE:
      final Operation pubKeySigOp = ScriptOperationUtil.getOperation(value.getBytes()[0] & 0xFF);
      if (ScriptOperationUtil.isDataPushOperation(pubKeySigOp)) {
        return "PubKeySig operation: " + pubKeySigOp.name() + " (" + (value.getBytes()[0] & 0xFF) + " byte)";
      } else {
        return "PubKeySig operation: " + pubKeySigOp.name();
      }
    case SCRIPT_SIG_OP_CODE:
      final Operation scriptSigOp = ScriptOperationUtil.getOperation(value.getBytes()[0] & 0xFF);
      if (ScriptOperationUtil.isDataPushOperation(scriptSigOp)) {
        return "ScriptSig operation: " + scriptSigOp.name() + " (" + (value.getBytes()[0] & 0xFF) + " byte)";
      } else {
        return "ScriptSig operation: " + scriptSigOp.name();
      }
    case SCRIPT_PUB_KEY_PUSH_DATA:
      return "PubKeySig data";
    case SCRIPT_SIG_PUSH_DATA:
      return "ScriptSig data";
    case LOCK_TIME:
      return "Transaction lock time";
    case VERSION:
      return "Transaction version";
    default:
      return "Unknown transaction part";
    }
  }
}
