package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;
import com.yoghurt.crypto.transactions.client.util.address.AddressEncodeUtil;
import com.yoghurt.crypto.transactions.client.util.address.AddressParseUtil;
import com.yoghurt.crypto.transactions.client.util.address.Base58;
import com.yoghurt.crypto.transactions.client.widget.AddressOutpointWidget;
import com.yoghurt.crypto.transactions.client.widget.QRCodeWidget;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.shared.domain.Address;
import com.yoghurt.crypto.transactions.shared.domain.Base58CheckContents;
import com.yoghurt.crypto.transactions.shared.service.domain.AddressInformation;
import com.yoghurt.crypto.transactions.shared.service.domain.AddressOutpoint;

public class AddressViewImpl extends Composite implements AddressView {
  interface AddressViewImplUiBinder extends UiBinder<Widget, AddressViewImpl> {}

  private static final AddressViewImplUiBinder UI_BINDER = GWT.create(AddressViewImplUiBinder.class);

  @UiField ValueViewer balanceViewer;
  @UiField ValueViewer transactionAmountViewer;

  @UiField ValueViewer addressViewer;
  @UiField ValueViewer addressHexViewer;
  @UiField ValueViewer versionViewer;
  @UiField ValueViewer payloadViewer;
  @UiField ValueViewer checksumViewer;

  @UiField FlowPanel wellFormedContainer;
  @UiField QRCodeWidget qrCode;

  @UiField FlowPanel outpointContainer;

  @UiField FlowPanel malformedContainer;
  @UiField ValueViewer validityViewer;
  @UiField ValueViewer advertisedChecksumViewer;
  @UiField ValueViewer computedChecksumViewer;

  private final BitcoinPlaceRouter router;

  private LazyProgressListener progressListener;

  @Inject
  public AddressViewImpl(final BitcoinPlaceRouter router) {
    this.router = router;

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setAddress(final Base58CheckContents addressParts) {
    final Address address = AddressParseUtil.parseAddress(addressParts);

    final byte[] addressBytes = AddressEncodeUtil.encodeAddress(address);
    final String addressBase58 = Base58.encode(addressBytes);

    addressHexViewer.setValue(Str.toString(Hex.encode(addressBytes)).toUpperCase());
    addressViewer.setValue(addressBase58);

    versionViewer.setValue(address.getVersion());
    payloadViewer.setValue(address.getHash160());
    checksumViewer.setValue(AddressParseUtil.getChecksum(address));

    final boolean valid = AddressParseUtil.isValid(addressParts);

    malformedContainer.setVisible(!valid);
    wellFormedContainer.setVisible(valid);

    if (!valid) {
      validityViewer.setValue(String.valueOf(valid).toUpperCase());
      advertisedChecksumViewer.setValue(addressParts.getChecksum());
      computedChecksumViewer.setValue(AddressParseUtil.getChecksum(address));
    } else {
      qrCode.setValue(addressBase58);
    }
  }

  @Override
  public void setAddressInformation(final AddressInformation addressInformation) {
    transactionAmountViewer.setValue(addressInformation.getOutpoints().size());

    long balance = 0;
    int count = 0;
    for (final AddressOutpoint addressOutpoint : addressInformation.getOutpoints()) {
      outpointContainer.add(new AddressOutpointWidget(router, addressOutpoint, ++count));
      if(!addressOutpoint.isSpent()) {
        balance += addressOutpoint.getOutput().getTransactionValue();
      }
    }

    balanceViewer.setValue(balance / 100000000d + " BTC");

    progressListener.progressComplete();
  }

  @Override
  public void subscribeProgressListener(final LazyProgressListener progressListener) {
    this.progressListener = progressListener;
  }
}
