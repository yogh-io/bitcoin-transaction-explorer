package com.yoghurt.crypto.transactions.client.widget;

import java.util.Collection;

import com.google.gwt.dom.client.Style.Unit;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.domain.ScriptPart;
import com.yoghurt.crypto.transactions.client.domain.ScriptPartType;
import com.yoghurt.crypto.transactions.client.domain.ScriptType;
import com.yoghurt.crypto.transactions.client.util.ScriptOperationUtil;
import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.client.util.misc.EllipsisUtil;

public class ScriptViewer extends ContextFieldSet<ScriptPart> {
  private static final int HASH_ELLIPSIS = 16;
  private final ScriptType type;

  public ScriptViewer(final ScriptType type, final boolean isCoinbase) {
    super(new SimpleScriptContextFactory() {
      @Override
      public String getFieldText(final ScriptPart value) {
        if(isCoinbase) {
          return "Coinbase input data including extraNonce and miner notes.";
        } else {
          return super.getFieldText(value);
        }
      }
    });

    this.type = type;
  }

  public void setScript(final Collection<ScriptPart> instructions) {
    clear();
    for (final ScriptPart instruction : instructions) {
      addField(instruction);
    }
  }

  @Override
  protected Color getFieldColor(final ScriptPart value) {
    if(ScriptOperationUtil.isDataPushOperation(value.getOperation())) {
      return TransactionPartColorPicker.getFieldColor(ScriptOperationUtil.getScriptPartType(type, ScriptPartType.PUSH_DATA));
    } else {
      return TransactionPartColorPicker.getFieldColor(ScriptOperationUtil.getScriptPartType(type, ScriptPartType.OP_CODE));
    }
  }

  @Override
  protected String getFieldText(final ScriptPart value) {
    if(ScriptOperationUtil.isDataPushOperation(value.getOperation())) {
      return EllipsisUtil.applyInner(Str.toString(Hex.encode(value.getBytes())).toUpperCase(), HASH_ELLIPSIS);
    } else {
      return value.getOperation().name();
    }
  }

  @Override
  protected ContextField<ScriptPart> addField(final ContextField<ScriptPart> field) {
    field.getElement().getStyle().setMarginRight(10, Unit.PX);

    return super.addField(field);
  }
}
