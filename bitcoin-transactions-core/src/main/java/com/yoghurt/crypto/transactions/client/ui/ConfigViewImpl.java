package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.i18n.M;
import com.yoghurt.crypto.transactions.client.util.StyleUtil;
import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;
import com.yoghurt.crypto.transactions.shared.domain.config.RetrievalHookConfig;

public class ConfigViewImpl extends Composite implements ConfigView {
  private static final BlockchainSource[] compatibleSources = BlockchainSource.values();

  interface ConfigViewImplUiBinder extends UiBinder<Widget, ConfigViewImpl> {}

  private static final ConfigViewImplUiBinder UI_BINDER = GWT.create(ConfigViewImplUiBinder.class);

  @UiField DeckPanel switchPanel;

  @UiField Button login;

  @UiField TextBox password;
  @UiField TextBox createPassword;
  @UiField TextBox createPasswordRepeat;

  @UiField ListBox connectorListBox;
  @UiField SimplePanel configEditorContainer;

  private Presenter presenter;

  @SuppressWarnings("rawtypes")
  private ConfigEditor currentEditor;

  public ConfigViewImpl() {
    initWidget(UI_BINDER.createAndBindUi(this));

    for (final BlockchainSource source : compatibleSources) {
      connectorListBox.addItem(M.messages().configConnectorOption(source), source.name());
    }
    DomEvent.fireNativeEvent(Document.get().createChangeEvent(), connectorListBox);

    StyleUtil.setPlaceHolder(createPassword, M.messages().configPasswordPlaceHolder());
    StyleUtil.setPlaceHolder(password, M.messages().configPasswordPlaceHolder());
    StyleUtil.setPlaceHolder(createPasswordRepeat, M.messages().configPasswordRepeatPlaceHolder());
  }

  @Override
  public void setPresenter(final Presenter presenter) {
    this.presenter = presenter;
  }

  @UiHandler("submitNewPassword")
  public void onSubmitNewPasswordClick(final ClickEvent e) {
    if (createPassword.getValue().equals(createPasswordRepeat.getValue())) {
      presenter.submitNewPassword(createPassword.getText());
    } else {
      // Show error
    }
  }

  @UiHandler("saveConfig")
  public void onSaveConfigClick(final ClickEvent e) {
    presenter.saveConfig();
  }

  @UiHandler("connectorListBox")
  public void onConnectorListValueChange(final ChangeEvent e) {
    displayConfigEditor(BlockchainSource.valueOf(connectorListBox.getSelectedValue()));
  }

  private void displayConfigEditor(final BlockchainSource source) {
    currentEditor = createEditor(source);

    configEditorContainer.setWidget(currentEditor);
  }

  @UiHandler("login")
  public void onLoginClick(final ClickEvent e) {
    presenter.checkAuthenticed(password.getValue());
    login.setEnabled(false);
  }

  @Override
  public void setCreatePassword(final boolean create) {
    login.setEnabled(true);
    switchPanel.showWidget(create ? 0 : 1);
  }

  @Override
  public void setAuthentic(final boolean result) {
    login.setEnabled(true);
    switchPanel.showWidget(2);
  }

  private ConfigEditor<?> createEditor(final BlockchainSource source) {
    switch (source) {
    case BLOCKR_API:
      return new BlockrConfigEditor();
    case NODE:
      return new NodeConfigEditor();
    }

    return null;
  }

  @Override
  public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<RetrievalHookConfig> handler) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public RetrievalHookConfig getValue() {
    return (RetrievalHookConfig) currentEditor.getValue();
  }

  @Override
  public void setValue(final RetrievalHookConfig value) {
    setValue(value, false);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void setValue(final RetrievalHookConfig value, final boolean fireEvents) {
    for (int i = 0; i < connectorListBox.getItemCount(); i++) {
      if(connectorListBox.getValue(i).equals(value.getBlockchainSource().name())) {
        connectorListBox.setSelectedIndex(i);
        DomEvent.fireNativeEvent(Document.get().createChangeEvent(), connectorListBox);
        break;
      }
    }

    currentEditor.setValue(value);
  }
}
