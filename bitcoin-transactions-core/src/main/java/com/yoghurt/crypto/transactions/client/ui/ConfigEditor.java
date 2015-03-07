package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.shared.domain.config.RetrievalHookConfig;

public interface ConfigEditor<E extends RetrievalHookConfig> extends IsWidget, LeafValueEditor<E> {

}
