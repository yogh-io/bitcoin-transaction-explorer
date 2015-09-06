package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.place.AddressPlaceRouter;
import com.yoghurt.crypto.transactions.client.util.address.AddressParseUtil;
import com.yoghurt.crypto.transactions.shared.domain.Address;
import com.yoghurt.crypto.transactions.shared.domain.ScriptType;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutput;

public class TransactionOutputWidget extends Composite {
  interface TransactionOutputWidgetUiBinder extends UiBinder<Widget, TransactionOutputWidget> {}

  private static final TransactionOutputWidgetUiBinder UI_BINDER = GWT.create(TransactionOutputWidgetUiBinder.class);

  @UiField HeadingWidget outputTitle;
  @UiField ValueViewer amountViewer;
  @UiField(provided = true) ScriptViewer signatureScriptViewer;

  @UiField LabelledWidget outputContainer;
  @UiField(provided = true) AddressViewer outputAddress;

  public TransactionOutputWidget(final AddressPlaceRouter router, final TransactionOutput output) {
    outputAddress = new AddressViewer(router);
    signatureScriptViewer = new ScriptViewer(ScriptType.SCRIPT_PUB_KEY, false);

    initWidget(UI_BINDER.createAndBindUi(this));

    outputTitle.setText("Output #" + output.getOutputIndex());
    amountViewer.setValue(output.getTransactionValue() / 100000000d + " BTC");

    signatureScriptViewer.setScript(output.getInstructions());

    final Address address = AddressParseUtil.tryParseAddress(output);
    outputContainer.setVisible(address != null);
    if(address != null) {
      outputAddress.setValue(address);
    }
  }
}
