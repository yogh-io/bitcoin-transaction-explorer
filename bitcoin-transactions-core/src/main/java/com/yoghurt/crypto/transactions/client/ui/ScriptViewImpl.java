package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;
import com.yoghurt.crypto.transactions.client.i18n.M;
import com.yoghurt.crypto.transactions.client.util.script.ExecutionStep;
import com.yoghurt.crypto.transactions.client.util.script.StackMachine;
import com.yoghurt.crypto.transactions.client.util.transaction.ScriptEncodeUtil;
import com.yoghurt.crypto.transactions.client.widget.ScriptExecutionViewer;
import com.yoghurt.crypto.transactions.client.widget.ScriptHexViewer;
import com.yoghurt.crypto.transactions.client.widget.ScriptStateViewer;
import com.yoghurt.crypto.transactions.client.widget.ScriptViewer;
import com.yoghurt.crypto.transactions.client.widget.TransactionViewer;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.shared.domain.RawTransactionContainer;
import com.yoghurt.crypto.transactions.shared.domain.ScriptInformation;
import com.yoghurt.crypto.transactions.shared.domain.ScriptType;

@Singleton
public class ScriptViewImpl extends Composite implements ScriptView {
  interface ScriptViewImplUiBinder extends UiBinder<Widget, ScriptViewImpl> {}

  private static final ScriptViewImplUiBinder UI_BINDER = GWT.create(ScriptViewImplUiBinder.class);

  @UiField(provided = true) TransactionViewer hashViewer;
  @UiField ValueViewer indexViewer;

  @UiField ScriptHexViewer pubKeySigHexViewer;
  @UiField ScriptHexViewer scriptSigHexViewer;

  @UiField FlowPanel scriptExecutionContainer;

  @UiField Label scriptExecutionResult;

  @UiField(provided = true) ScriptViewer scriptSigViewer;
  @UiField(provided = true) ScriptViewer pubKeySigViewer;
  @UiField ScriptExecutionViewer fullScriptViewer;

  @Inject
  public ScriptViewImpl(final BitcoinPlaceRouter router) {
    hashViewer = new TransactionViewer(router, false, false);
    scriptSigViewer = new ScriptViewer(ScriptType.SCRIPT_SIG, false);
    pubKeySigViewer = new ScriptViewer(ScriptType.SCRIPT_PUB_KEY, false);

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setScript(final ScriptInformation information, final StackMachine stackMachine) {
    hashViewer.setValue(information.getOutpoint().getReferenceTransaction());
    indexViewer.setValue(information.getOutpoint().getIndex());

    pubKeySigViewer.setScript(information.getPubKeySig().getInstructions());
    scriptSigViewer.setScript(information.getScriptSig().getInstructions());

    fullScriptViewer.setScript(stackMachine.getScript());

    final RawTransactionContainer rawPubKeySigContainer = new RawTransactionContainer();
    ScriptEncodeUtil.encodeScript(information.getPubKeySig(), rawPubKeySigContainer, ScriptType.SCRIPT_PUB_KEY);

    final RawTransactionContainer rawScriptSigContainer = new RawTransactionContainer();
    ScriptEncodeUtil.encodeScript(information.getScriptSig(), rawScriptSigContainer, ScriptType.SCRIPT_SIG);

    pubKeySigHexViewer.setValue(rawPubKeySigContainer);
    scriptSigHexViewer.setValue(rawScriptSigContainer);

    scriptExecutionContainer.clear();

    int counter = 1;
    for(final ExecutionStep state : stackMachine) {
      final ScriptStateViewer scriptViewer = new ScriptStateViewer(state, counter);
      scriptExecutionContainer.add(scriptViewer);

      if(state.hasExecutionError()) {
        break;
      }

      counter++;
    }

    if(stackMachine.hasExecutionError()) {
      scriptExecutionResult.setText(M.messages().scriptPlaceExecutionResultFailureTriggered());
    } else if(stackMachine.getStack().isEmpty() || stackMachine.getStack().peek().getBytes().length == 0) {
      scriptExecutionResult.setText(M.messages().scriptPlaceExecutionResultFailureResult());
    } else {
      scriptExecutionResult.setText(M.messages().scriptPlaceExecutionResultSuccess());
    }
  }
}
