package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.util.misc.ColorBuilder;
import com.yoghurt.crypto.transactions.shared.domain.ScriptType;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutput;

public class TransactionOutputWidget extends Composite {
  interface TransactionOutputWidgetUiBinder extends UiBinder<Widget, TransactionOutputWidget> {}

  private static final TransactionOutputWidgetUiBinder UI_BINDER = GWT.create(TransactionOutputWidgetUiBinder.class);

  @UiField HeadingElement outputTitle;
  @UiField(provided = true) ValueViewer amountViewer;
  @UiField(provided = true) ScriptViewer signatureScriptViewer;

  public TransactionOutputWidget(final TransactionOutput output) {
    amountViewer = new ValueViewer(ColorBuilder.interpret("yellow"));
    signatureScriptViewer = new ScriptViewer(ScriptType.SCRIPT_PUB_KEY, false);

    initWidget(UI_BINDER.createAndBindUi(this));

    outputTitle.setInnerText("Output #" + output.getOutputIndex());
    amountViewer.setValue(output.getTransactionValue() / 100000000d + " BTC");
    signatureScriptViewer.setScript(output.getInstructions());
  }
}
