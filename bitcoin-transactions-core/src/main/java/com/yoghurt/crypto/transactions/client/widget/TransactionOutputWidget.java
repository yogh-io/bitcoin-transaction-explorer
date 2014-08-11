package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.domain.transaction.TransactionOutput;
import com.yoghurt.crypto.transactions.client.util.ColorBuilder;
import com.yoghurt.crypto.transactions.client.util.ScriptType;
import com.yoghurt.crypto.transactions.client.widget.script.ScriptViewer;

public class TransactionOutputWidget extends Composite {
  interface TransactionOutputWidgetUiBinder extends UiBinder<Widget, TransactionOutputWidget> {}

  private static final TransactionOutputWidgetUiBinder UI_BINDER = GWT.create(TransactionOutputWidgetUiBinder.class);

  @UiField(provided = true) ValueViewer amountViewer;
  @UiField(provided = true) ScriptViewer signatureScriptViewer;

  public TransactionOutputWidget(final TransactionOutput output) {
    amountViewer = new ValueViewer(ColorBuilder.interpret("yellow"));
    signatureScriptViewer = new ScriptViewer(ScriptType.SCRIPT_PUB_KEY);

    initWidget(UI_BINDER.createAndBindUi(this));

    amountViewer.setValue(output.getTransactionValue() / 100000000d + " BTC");
    signatureScriptViewer.setScript(output.getInstructions());
  }
}
