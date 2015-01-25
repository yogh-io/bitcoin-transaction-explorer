package com.yoghurt.crypto.transactions.client.util.script;

import com.yoghurt.crypto.transactions.shared.domain.ScriptInformation;

public class StackMachineFactory {

  public static StackMachine createStackMachine(final ScriptInformation scriptInformation) {
    return new StackMachine(scriptInformation);
  }
}
