package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.util.address.AddressEncodeUtil;
import com.yoghurt.crypto.transactions.client.util.address.AddressParseUtil;
import com.yoghurt.crypto.transactions.client.util.address.Base58;
import com.yoghurt.crypto.transactions.client.util.address.Base58CheckContents;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.shared.domain.Address;
import com.yoghurt.crypto.transactions.shared.domain.AddressInformation;

public class AddressViewImpl extends Composite implements AddressView {
  interface AddressViewImplUiBinder extends UiBinder<Widget, AddressViewImpl> {}

  private static final AddressViewImplUiBinder UI_BINDER = GWT.create(AddressViewImplUiBinder.class);

  @UiField ValueViewer addressViewer;
  @UiField ValueViewer addressHexViewer;
  @UiField ValueViewer versionViewer;
  @UiField ValueViewer payloadViewer;
  @UiField ValueViewer checksumViewer;

  @UiField FlowPanel malformedContainer;
  @UiField ValueViewer validityViewer;
  @UiField ValueViewer advertisedChecksumViewer;
  @UiField ValueViewer computedChecksumViewer;

  public AddressViewImpl() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setAddress(final Base58CheckContents addressParts) {
    final Address address = AddressParseUtil.parseAddress(addressParts);

    final byte[] addressBytes = AddressEncodeUtil.encodeAddress(address);

    addressHexViewer.setValue(Str.toString(Hex.encode(addressBytes)).toUpperCase());
    addressViewer.setValue(Base58.encode(addressBytes));

    versionViewer.setValue(address.getVersion());
    payloadViewer.setValue(address.getHash160());
    checksumViewer.setValue(AddressParseUtil.getChecksum(address));

    final boolean valid = AddressParseUtil.isValid(addressParts);

    malformedContainer.setVisible(!valid);
    validityViewer.setValue(String.valueOf(valid).toUpperCase());
    advertisedChecksumViewer.setValue(addressParts.getChecksum());
    computedChecksumViewer.setValue(AddressParseUtil.getChecksum(address));
  }

  @Override
  public void setAddressInformation(final AddressInformation addressInformation) {

  }
}
