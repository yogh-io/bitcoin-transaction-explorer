package com.yoghurt.crypto.transactions.client.widget;

import java.util.Map.Entry;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.util.transaction.ScriptOperationUtil;
import com.yoghurt.crypto.transactions.client.widget.ContextFieldSet.FieldContextFactory;
import com.yoghurt.crypto.transactions.shared.domain.Operation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;
import com.yoghurt.crypto.transactions.shared.domain.VariableLengthInteger;
import com.yoghurt.crypto.transactions.shared.util.NumberParseUtil;

public class SimpleTransactionContextWidget implements FieldContextFactory<Entry<TransactionPartType, byte[]>> {
  @Override
  public Widget getContextWidget(final Entry<TransactionPartType, byte[]> value) {
    final Label label = new Label(getTextForPart(value));
    label.getElement().getStyle().setPadding(10, Unit.PX);
    return label;
  }

  private String getTextForPart(final Entry<TransactionPartType, byte[]> value) {
    switch (value.getKey()) {
    case INPUT_PREVIOUS_TRANSACTION_HASH:
      return "Previous transaction hash";
    case INPUT_OUTPOINT_INDEX:
      return "Previous output index: " + NumberParseUtil.parseUint32(value.getValue());
    case OUTPUT_SCRIPT_LENGTH:
      return "Output script length: " + new VariableLengthInteger(value.getValue()).getValue() + " byte";
    case INPUT_SCRIPT_LENGTH:
      return "Input script length: " + new VariableLengthInteger(value.getValue()).getValue() + " byte";
    case INPUT_SEQUENCE:
      return "Input sequence number";
    case INPUT_SIZE:
      return "Number of inputs: " + new VariableLengthInteger(value.getValue()).getValue();
    case FEE:
      return "Explicit transaction fee: " + NumberParseUtil.parseLong(value.getValue());
    case OUTPUT_SIZE:
      return "Number of outputs: " + new VariableLengthInteger(value.getValue()).getValue();
    case OUTPUT_VALUE:
      return "Output value: " + NumberParseUtil.parseLong(value.getValue()) / 100000000d + " BTC";
    case SCRIPT_PUB_KEY_OP_CODE:
      final Operation pubKeySigOp = ScriptOperationUtil.getOperation(value.getValue()[0] & 0xFF);
      if (ScriptOperationUtil.isDataPushOperation(pubKeySigOp)) {
        return "ScriptPubKey operation: " + pubKeySigOp.name() + " (" + (value.getValue()[0] & 0xFF) + " byte)";
      } else {
        return "ScriptPubKey operation: " + pubKeySigOp.name();
      }
    case SCRIPT_SIG_OP_CODE:
      final Operation scriptSigOp = ScriptOperationUtil.getOperation(value.getValue()[0] & 0xFF);
      if (ScriptOperationUtil.isDataPushOperation(scriptSigOp)) {
        return "ScriptSig operation: " + scriptSigOp.name() + " (" + (value.getValue()[0] & 0xFF) + " byte)";
      } else {
        return "ScriptSig operation: " + scriptSigOp.name();
      }
    case SCRIPT_PUB_KEY_PUSH_DATA:
      return "ScriptPubKey data";
    case SCRIPT_SIG_PUSH_DATA:
      return "ScriptSig data";
    case LOCK_TIME:
      return "Transaction lock time";
    case VERSION:
      return "Transaction version";
    case COINBASE_SCRIPT_SIG:
      return "Coinbase input script";
    default:
      return "Unknown transaction part";
    }
  }
}
