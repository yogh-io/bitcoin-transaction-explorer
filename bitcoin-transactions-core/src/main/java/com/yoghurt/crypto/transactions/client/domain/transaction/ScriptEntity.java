package com.yoghurt.crypto.transactions.client.domain.transaction;

import com.yoghurt.crypto.transactions.client.util.VariableInteger;

public interface ScriptEntity {
  VariableInteger getScriptSize();

  void setScriptSize(final VariableInteger scriptSize);

  SignatureScript getSignatureScript();

  void setSignatureScript(final SignatureScript signatureScript);
}
