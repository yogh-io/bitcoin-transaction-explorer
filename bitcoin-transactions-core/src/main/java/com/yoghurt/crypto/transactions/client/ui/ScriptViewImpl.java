package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;
import com.yoghurt.crypto.transactions.client.widget.ScriptViewer;
import com.yoghurt.crypto.transactions.client.widget.TransactionViewer;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.shared.domain.ScriptInformation;
import com.yoghurt.crypto.transactions.shared.domain.ScriptType;

@Singleton
public class ScriptViewImpl extends Composite implements ScriptView {
  interface ScriptViewImplUiBinder extends UiBinder<Widget, ScriptViewImpl> {}

  private static final ScriptViewImplUiBinder UI_BINDER = GWT.create(ScriptViewImplUiBinder.class);

  @UiField(provided = true) TransactionViewer hashViewer;
  @UiField ValueViewer indexViewer;

  @UiField(provided = true) ScriptViewer scriptSigViewer;
  @UiField(provided = true) ScriptViewer pubKeySigViewer;

  @Inject
  public ScriptViewImpl(final BitcoinPlaceRouter router) {
    hashViewer = new TransactionViewer(router, false, false);
    scriptSigViewer = new ScriptViewer(ScriptType.SCRIPT_SIG, false);
    pubKeySigViewer = new ScriptViewer(ScriptType.SCRIPT_PUB_KEY, false);

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setScript(final ScriptInformation information) {
    hashViewer.setValue(information.getOutpoint().getReferenceTransaction());
    indexViewer.setValue(information.getOutpoint().getIndex());

    scriptSigViewer.setScript(information.getScriptSig().getInstructions());
    pubKeySigViewer.setScript(information.getPubKeySig().getInstructions());
  }
}
