package com.yoghurt.crypto.transactions.client.widget.script;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.domain.transaction.script.Operation;
import com.yoghurt.crypto.transactions.client.domain.transaction.script.ScriptPart;
import com.yoghurt.crypto.transactions.client.widget.ContextFieldSet.FieldContextFactory;

public class SimpleScriptContextFactory implements FieldContextFactory<ScriptPart> {
  @Override
  public Widget getContextWidget(final ScriptPart value) {
    final Label label = new Label(getFieldText(value));
    label.getElement().getStyle().setPadding(10, Unit.PX);
    return label;
  }

  public String getFieldText(final ScriptPart value) {
    if (value.getOperation() == Operation.OP_PUSHDATA) {
      return new String(Hex.encode(value.getBytes())).toUpperCase();
    } else {
      return value.getOperation().name();
    }
  }
}
