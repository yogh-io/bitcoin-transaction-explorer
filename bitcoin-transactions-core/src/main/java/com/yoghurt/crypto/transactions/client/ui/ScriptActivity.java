package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.place.ScriptPlace;
import com.yoghurt.crypto.transactions.client.place.ScriptPlace.ScriptDataType;
import com.yoghurt.crypto.transactions.client.util.MorphCallback;
import com.yoghurt.crypto.transactions.client.util.ParseUtil;
import com.yoghurt.crypto.transactions.client.util.script.StackMachine;
import com.yoghurt.crypto.transactions.client.util.script.StackMachineFactory;
import com.yoghurt.crypto.transactions.shared.domain.ScriptEntity;
import com.yoghurt.crypto.transactions.shared.domain.ScriptInformation;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutPoint;
import com.yoghurt.crypto.transactions.shared.domain.TransactionOutput;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;
import com.yoghurt.crypto.transactions.shared.util.ScriptParseUtil;

public class ScriptActivity extends LookupActivity<ScriptInformation, ScriptPlace> implements BlockView.Presenter {
  private final ScriptView view;

  @Inject
  public ScriptActivity(final ScriptView view, @Assisted final ScriptPlace place, final BlockchainRetrievalServiceAsync service) {
    super(place, service);
    this.view = view;
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final ScriptInformation scriptInformation) {
    panel.setWidget(view);

    final StackMachine machine = StackMachineFactory.createStackMachine(scriptInformation);

    view.setScript(scriptInformation, machine);
  }

  @Override
  protected void doDeferredError(final AcceptsOneWidget panel, final Throwable caught) {
    // Not supported
  }

  @Override
  protected boolean mustPerformLookup(final ScriptPlace place) {
    return ScriptDataType.ID == place.getType();
  }

  @Override
  protected ScriptInformation createInfo(final ScriptPlace place) {
    final ScriptInformation information = new ScriptInformation();

    final ScriptEntity pubKeySig = ScriptParseUtil.parseScript(Hex.decode(place.getPubKeySig()));
    information.setPubKeySig(pubKeySig);

    final ScriptEntity scriptSig = ScriptParseUtil.parseScript(Hex.decode(place.getScriptSig()));
    information.setScriptSig(scriptSig);

    return information;
  }

  @Override
  protected void doLookup(final ScriptPlace place, final AsyncCallback<ScriptInformation> callback) {
    final MorphCallback<TransactionInformation, ScriptInformation> morphCallback = new MorphCallback<TransactionInformation, ScriptInformation>(callback) {
      @Override
      protected ScriptInformation morphResult(final TransactionInformation result) {
        // Parse the outpoint transaction in full
        final Transaction tx = ParseUtil.getTransactionFromHex(result.getRawHex());

        // Parse the ScriptSig from the place
        final ScriptEntity scriptSig = ScriptParseUtil.parseScript(Hex.decode(place.getScriptSig()));

        // Construct the OutPoint from the retrieved transaction index in the place
        final TransactionOutPoint outPoint = new TransactionOutPoint();
        outPoint.setReferenceTransaction(tx.getTransactionId());
        outPoint.setIndex(place.getOutpointIndex());

        // Get the OutPoint's pubKeySig from the parsed transaction
        final TransactionOutput pubKeySig = tx.getOutputs().get(place.getOutpointIndex());

        // Construct the ScriptInformation and return it
        final ScriptInformation information = new ScriptInformation();
        information.setPubKeySig(pubKeySig);
        information.setScriptSig(scriptSig);
        information.setOutpoint(outPoint);
        return information;
      }
    };

    switch (place.getType()) {
    case ID:
      service.getTransactionInformation(place.getOutpointTransaction(), morphCallback);
      break;
    default:
      callback.onFailure(new IllegalStateException("No support lookup for type: " + place.getType().name()));
      return;
    }
  }
}