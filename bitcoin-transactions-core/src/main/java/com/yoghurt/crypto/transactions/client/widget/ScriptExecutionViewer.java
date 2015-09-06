package com.yoghurt.crypto.transactions.client.widget;

import java.util.Collection;

import com.google.gwt.dom.client.Style.Unit;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.client.util.misc.EllipsisUtil;
import com.yoghurt.crypto.transactions.shared.domain.ScriptExecutionPart;
import com.yoghurt.crypto.transactions.shared.domain.ScriptPartType;
import com.yoghurt.crypto.transactions.shared.util.ScriptOperationUtil;

public class ScriptExecutionViewer extends ContextFieldSet<ScriptExecutionPart> {
  private static final int HASH_ELLIPSIS = 16;

  public ScriptExecutionViewer() {
    super(new SimpleScriptExecutionContextFactory());
  }

  public void setScript(final Collection<ScriptExecutionPart> instructions) {
    clear();
    for (final ScriptExecutionPart instruction : instructions) {
      addField(instruction);
    }
  }

  @Override
  protected Color getFieldColor(final ScriptExecutionPart value) {
    if(ScriptOperationUtil.isDataPushOperation(value.getOperation())) {
      return TransactionPartColorPicker.getFieldColor(ScriptOperationUtil.getScriptPartType(value.getOrigin(), ScriptPartType.PUSH_DATA));
    } else {
      return TransactionPartColorPicker.getFieldColor(ScriptOperationUtil.getScriptPartType(value.getOrigin(), ScriptPartType.OP_CODE));
    }
  }

  @Override
  protected String getFieldText(final ScriptExecutionPart value) {
    if(ScriptOperationUtil.isDataPushOperation(value.getOperation())) {
      return EllipsisUtil.applyInner(Str.toString(Hex.encode(value.getBytes())).toUpperCase(), HASH_ELLIPSIS);
    } else {
      return value.getOperation().name();
    }
  }

  @Override
  protected ContextField<ScriptExecutionPart> addField(final ContextField<ScriptExecutionPart> field) {
    field.getElement().getStyle().setMarginRight(10, Unit.PX);

    return super.addField(field);
  }
}
