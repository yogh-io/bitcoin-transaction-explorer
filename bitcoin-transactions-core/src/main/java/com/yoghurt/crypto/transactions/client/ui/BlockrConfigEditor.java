package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.Label;
import com.yoghurt.crypto.transactions.client.i18n.M;
import com.yoghurt.crypto.transactions.shared.domain.config.BlockrRetrievalHookConfig;

public class BlockrConfigEditor extends Label implements ConfigEditor<BlockrRetrievalHookConfig> {

  public BlockrConfigEditor() {
    super(M.messages().configBlockrConfigNote());
  }

  @Override
  public void setValue(final BlockrRetrievalHookConfig value) {
  }

  @Override
  public BlockrRetrievalHookConfig getValue() {
    return new BlockrRetrievalHookConfig();
  }
}