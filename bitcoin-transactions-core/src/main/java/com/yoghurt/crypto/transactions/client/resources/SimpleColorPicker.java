package com.yoghurt.crypto.transactions.client.resources;

import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.client.util.misc.ColorBuilder;

public class SimpleColorPicker implements ColorPicker {
  @Override
  public Color blockHash() {
    return ColorBuilder.interpret("green");
  }

  @Override
  public Color blockHeight() {
    return ColorBuilder.interpret("green");
  }

  @Override
  public Color blockConfirmations() {
    return ColorBuilder.interpret("grey");
  }

  @Override
  public Color blockNumTransactions() {
    return ColorBuilder.interpret("cornflowerblue");
  }

  @Override
  public Color blockSize() {
    return ColorBuilder.interpret("red");
  }

  @Override
  public Color blockVersion() {
    return ColorBuilder.interpret("grey");
  }

  @Override
  public Color blockMerkleRoot() {
    return ColorBuilder.interpret("cornflowerblue");
  }

  @Override
  public Color blockTime() {
    return ColorBuilder.interpret("red");
  }

  @Override
  public Color blockBits() {
    return ColorBuilder.interpret("cyan");
  }

  @Override
  public Color blockNonce() {
    return ColorBuilder.interpret("lightgreen");
  }

  @Override
  public Color transactionHash() {
    return ColorBuilder.interpret("cornflowerblue");
  }

  @Override
  public Color transactionConfirmedState() {
    return ColorBuilder.interpret("grey");
  }

  @Override
  public Color transactionConfirmations() {
    return ColorBuilder.interpret("grey");
  }

  @Override
  public Color transactionTime() {
    return ColorBuilder.interpret("grey");
  }

  @Override
  public Color transactionInputLength() {
    return ColorBuilder.interpret("pink");
  }

  @Override
  public Color transactionInputIndex() {
    return ColorBuilder.interpret("lightblue");
  }

  @Override
  public Color transactionScriptSigLength() {
    return ColorBuilder.interpret("mediumvioletred");
  }

  @Override
  public Color transactionScriptSigOpCode() {
    return ColorBuilder.interpret("red");
  }

  @Override
  public Color transactionScriptSigPushData() {
    return ColorBuilder.interpret("cyan");
  }

  @Override
  public Color transactionInputSequence() {
    return ColorBuilder.interpret("lightgreen");
  }

  @Override
  public Color transactionOutputAmount() {
    return ColorBuilder.interpret("gold");
  }

  @Override
  public Color transactionOutputLength() {
    return ColorBuilder.interpret("pink");
  }

  @Override
  public Color transactionPubKeySigLength() {
    return ColorBuilder.interpret("mediumvioletred");
  }

  @Override
  public Color transactionPubKeySigOpCode() {
    return ColorBuilder.interpret("red");
  }

  @Override
  public Color transactionPubKeySigPushData() {
    return ColorBuilder.interpret("green");
  }

  @Override
  public Color transactionPubKeySigPushDataExtra() {
    return ColorBuilder.interpret("teal");
  }

  @Override
  public Color transactionVersion() {
    return ColorBuilder.interpret("grey");
  }

  @Override
  public Color transactionLockTime() {
    return ColorBuilder.interpret("grey");
  }

  @Override
  public Color transactionArbitraryData() {
    return ColorBuilder.interpret("black");
  }

  @Override
  public Color stackData() {
    return ColorBuilder.interpret("teal");
  }

  @Override
  public Color stackSingle() {
    return ColorBuilder.interpret("maroon");
  }
}
