package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.client.domain.ScriptInformation;
import com.yoghurt.crypto.transactions.client.util.script.StackMachine;

public interface ScriptView extends IsWidget {

  public interface Presenter {

  }

  void setScript(ScriptInformation information, StackMachine scriptSteps);
}
