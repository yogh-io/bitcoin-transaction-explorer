package com.yoghurt.crypto.transactions.client.ui;

import gwt.material.design.client.ui.MaterialTextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.shared.domain.config.BitcoinCoreNodeConfig;

public class NodeConfigEditor extends Composite implements ConfigEditor<BitcoinCoreNodeConfig> {

  interface NodeConfigEditorUiBinder extends UiBinder<Widget, NodeConfigEditor> {}

  private static final NodeConfigEditorUiBinder UI_BINDER = GWT.create(NodeConfigEditorUiBinder.class);

  @UiField MaterialTextBox hostField;
  @UiField MaterialTextBox portField;
  @UiField MaterialTextBox rpcUserField;
  @UiField MaterialTextBox rpcPassField;

  public NodeConfigEditor() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setValue(final BitcoinCoreNodeConfig value) {
    hostField.setText(value.getHost());
    portField.setText(value.getPort());
    rpcUserField.setText(value.getRpcUser());
    rpcPassField.setText(value.getRpcPass());
  }

  @Override
  public BitcoinCoreNodeConfig getValue() {
    final BitcoinCoreNodeConfig config = new BitcoinCoreNodeConfig();

    config.setHost(hostField.getValue());
    config.setPort(portField.getValue());
    config.setRpcUser(rpcUserField.getValue());
    config.setRpcPass(rpcPassField.getValue());

    return config;
  }

}
