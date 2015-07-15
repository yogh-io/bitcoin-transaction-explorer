package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.i18n.M;
import com.yoghurt.crypto.transactions.client.widget.HeadingWidget;
import com.yoghurt.crypto.transactions.client.widget.QRCodeWidget;
import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;
import com.yoghurt.crypto.transactions.shared.domain.config.UserApplicationConfig;

public class ContributeViewImpl extends Composite implements ContributeView {
  interface ContributeViewImplUiBinder extends UiBinder<Widget, ContributeViewImpl> {}

  private static final ContributeViewImplUiBinder UI_BINDER = GWT.create(ContributeViewImplUiBinder.class);

  @UiField HTML contributeProjectText;
  @UiField QRCodeWidget contributeProjectQRCode;

  @UiField HeadingWidget contributeNodeTitle;
  @UiField HTML contributeNodeText;
  @UiField QRCodeWidget contributeNodeQRCode;

  private UserApplicationConfig appConfig;

  public ContributeViewImpl() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  protected void onLoad() {
    if (appConfig != null) {
      setConfig(appConfig);
    }
  }

  @Override
  public void setConfig(final UserApplicationConfig appConfig) {
    this.appConfig = appConfig;

    contributeProjectText.setHTML(M.messages().contributePlaceText(appConfig.getProjectDonationAddress()));
    if (contributeProjectQRCode.isAttached()) {
      contributeProjectQRCode.setValue(appConfig.getProjectDonationAddress());
    }

    if (appConfig.getBlockchainSource() == BlockchainSource.NODE
        && appConfig.getHostDonationAddress() != null
        && !appConfig.getHostDonationAddress().isEmpty()) {
      contributeNodeText.setHTML(M.messages().contributePlaceNodeText(appConfig.getHostDonationAddress()));
      if (contributeNodeQRCode.isAttached()) {
        contributeNodeQRCode.setValue(appConfig.getHostDonationAddress());
      }
    } else {
      contributeNodeTitle.removeFromParent();
      contributeNodeText.removeFromParent();
      contributeNodeQRCode.removeFromParent();
    }
  }
}
