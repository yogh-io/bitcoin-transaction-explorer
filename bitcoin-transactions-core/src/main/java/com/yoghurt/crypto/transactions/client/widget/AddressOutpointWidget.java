package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;
import com.yoghurt.crypto.transactions.client.resources.R;
import com.yoghurt.crypto.transactions.shared.domain.AddressOutpoint;

public class AddressOutpointWidget extends Composite {
  interface AddressOutpointUiBinder extends UiBinder<Widget, AddressOutpointWidget> {}

  private static final AddressOutpointUiBinder UI_BINDER = GWT.create(AddressOutpointUiBinder.class);

  @UiField HeadingWidget outpointTitle;
  @UiField(provided = true) TransactionViewer hashViewer;
  @UiField ValueViewer indexViewer;
  @UiField ValueViewer amountViewer;
  @UiField ValueViewer spentViewer;

  public AddressOutpointWidget(final BitcoinPlaceRouter router, final AddressOutpoint outpoint, final int idx) {
    hashViewer = new TransactionViewer(router, false, false);

    initWidget(UI_BINDER.createAndBindUi(this));

    outpointTitle.setText("Outpoint #" + idx);

    hashViewer.setValue(outpoint.getReferenceTransaction());
    indexViewer.setValue(String.valueOf(outpoint.getIndex()));
    amountViewer.setValue(outpoint.getOutput().getTransactionValue() / 100000000d + " BTC");

    final boolean spent = outpoint.isSpent();

    spentViewer.setColor(spent ? R.color().addressOutpointSpent() : R.color().addressOutpointUnspent());
    spentViewer.setValue(String.valueOf(spent).toUpperCase());
  }

}
