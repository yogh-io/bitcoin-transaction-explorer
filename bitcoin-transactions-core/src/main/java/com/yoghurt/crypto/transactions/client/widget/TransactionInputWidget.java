package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.domain.transaction.TransactionInput;

public class TransactionInputWidget extends Composite {
  interface TransactionInputWidgetUiBinder extends UiBinder<Widget, TransactionInputWidget> {}

  private static final TransactionInputWidgetUiBinder UI_BINDER = GWT.create(TransactionInputWidgetUiBinder.class);

  @UiField LabelledValue previousOutputField;
  @UiField LabelledValue signatureScriptField;
  @UiField LabelledValue sequenceField;

  public TransactionInputWidget(final TransactionInput input) {
    initWidget(UI_BINDER.createAndBindUi(this));

    previousOutputField.setValue(new String(Hex.encode(input.getOutPoint().getReferenceTransaction())));


    sequenceField.setValue(String.valueOf(input.getTransactionSequence()));
  }
}
