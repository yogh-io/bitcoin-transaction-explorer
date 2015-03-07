package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.i18n.M;
import com.yoghurt.crypto.transactions.client.util.StyleUtil;
import com.yoghurt.crypto.transactions.shared.domain.config.BitcoinCoreNodeConfig;

public class NodeConfigEditor extends Composite implements ConfigEditor<BitcoinCoreNodeConfig> {

  interface NodeConfigEditorUiBinder extends UiBinder<Widget, NodeConfigEditor> {}

  private static final NodeConfigEditorUiBinder UI_BINDER = GWT.create(NodeConfigEditorUiBinder.class);

  @UiField TextBox hostField;
  @UiField TextBox portField;
  @UiField TextBox rpcUserField;
  @UiField TextBox rpcPassField;

  public NodeConfigEditor() {
    initWidget(UI_BINDER.createAndBindUi(this));

    StyleUtil.setPlaceHolder(hostField, M.messages().configNodeHost());
    StyleUtil.setPlaceHolder(portField, M.messages().configNodePort());
    StyleUtil.setPlaceHolder(rpcUserField, M.messages().configNodeRpcUser());
    StyleUtil.setPlaceHolder(rpcPassField, M.messages().configNodeRpcPass());
  }

  @Override
  public void setValue(final BitcoinCoreNodeConfig value) {
    hostField.setValue(value.getHost());
    portField.setValue(value.getPort());
    rpcUserField.setValue(value.getRpcUser());
    rpcPassField.setValue(value.getRpcPass());
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
