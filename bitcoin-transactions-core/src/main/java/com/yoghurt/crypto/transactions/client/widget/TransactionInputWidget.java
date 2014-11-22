package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.widget.script.ScriptViewer;
import com.yoghurt.crypto.transactions.shared.domain.ScriptType;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;
import com.yoghurt.crypto.transactions.shared.util.NumberEncodeUtil;

public class TransactionInputWidget extends Composite {
  interface TransactionInputWidgetUiBinder extends UiBinder<Widget, TransactionInputWidget> {}

  private static final TransactionInputWidgetUiBinder UI_BINDER = GWT.create(TransactionInputWidgetUiBinder.class);

  @UiField(provided = true) ValueViewer hashViewer;
  @UiField(provided = true) ScriptViewer signatureScriptViewer;
  @UiField(provided = true) ValueViewer indexViewer;
  @UiField(provided = true) ValueViewer sequenceViewer;

  public TransactionInputWidget(final TransactionInput input) {
    hashViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.TRANSACTION_HASH));
    signatureScriptViewer = new ScriptViewer(ScriptType.SCRIPT_SIG);
    indexViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.INPUT_OUTPOINT_INDEX));
    sequenceViewer = new ValueViewer(TransactionPartColorPicker.getFieldColor(TransactionPartType.INPUT_SEQUENCE));

    initWidget(UI_BINDER.createAndBindUi(this));

    hashViewer.setValue(input.getOutPoint().getReferenceTransaction());
    signatureScriptViewer.setScript(input.getInstructions());
    indexViewer.setValue(String.valueOf(input.getOutPoint().getIndex()));
    sequenceViewer.setValue(Str.toString(Hex.encode(NumberEncodeUtil.encodeUint32(input.getTransactionSequence()))).toUpperCase());
  }
}
