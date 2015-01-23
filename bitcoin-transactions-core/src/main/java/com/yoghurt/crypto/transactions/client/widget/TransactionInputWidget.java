package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;
import com.yoghurt.crypto.transactions.shared.domain.ScriptType;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInput;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutPoint;
import com.yoghurt.crypto.transactions.shared.util.NumberEncodeUtil;
import com.yoghurt.crypto.transactions.shared.util.transaction.ScriptEncodeUtil;

public class TransactionInputWidget extends Composite {
  interface TransactionInputWidgetUiBinder extends UiBinder<Widget, TransactionInputWidget> {}

  private static final TransactionInputWidgetUiBinder UI_BINDER = GWT.create(TransactionInputWidgetUiBinder.class);

  @UiField HeadingElement inputTitle;
  @UiField(provided = true) TransactionViewer hashViewer;
  @UiField(provided = true) ScriptViewer signatureScriptViewer;
  @UiField ValueViewer indexViewer;
  @UiField ValueViewer sequenceViewer;
  @UiField Label scriptViewField;

  private final BitcoinPlaceRouter router;
  private final TransactionInput input;

  public TransactionInputWidget(final TransactionInput input, final BitcoinPlaceRouter router) {
    this.input = input;
    this.router = router;

    hashViewer = new TransactionViewer(router, input.isCoinbase());
    signatureScriptViewer = new ScriptViewer(ScriptType.SCRIPT_SIG, input.isCoinbase());

    initWidget(UI_BINDER.createAndBindUi(this));

    inputTitle.setInnerText("Input #" + input.getInputIndex());
    hashViewer.setValue(input.getOutPoint().getReferenceTransaction());
    signatureScriptViewer.setScript(input.getInstructions());
    indexViewer.setValue(String.valueOf(input.getOutPoint().getIndex()));
    sequenceViewer.setValue(Str.toString(Hex.encode(NumberEncodeUtil.encodeUint32(input.getTransactionSequence()))).toUpperCase());

    scriptViewField.setVisible(!input.isCoinbase());
  }

  @UiHandler("scriptViewField")
  public void onScriptViewClick(final ClickEvent e) {
    final TransactionOutPoint outPoint = input.getOutPoint();

    final String scriptSig = Str.toString(Hex.encode(ScriptEncodeUtil.encodeScript(input)));

    router.goToScript(Str.toString(Hex.encode(outPoint.getReferenceTransaction())), (int) outPoint.getIndex(), scriptSig);
  }
}
