package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.yoghurt.crypto.transactions.client.util.script.ExecutionStep;
import com.yoghurt.crypto.transactions.shared.domain.ScriptInformation;

public interface ScriptView extends IsWidget {

  public interface Presenter {

  }

  void setScript(ScriptInformation information, Iterable<ExecutionStep> stack);
}
