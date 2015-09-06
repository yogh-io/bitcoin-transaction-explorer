package com.yoghurt.crypto.transactions.client.resources;

import com.yoghurt.crypto.transactions.client.util.misc.Color;

public interface ColorPicker {
  Color blockHash();

  Color blockHeight();

  Color blockConfirmations();

  Color blockNumTransactions();

  Color blockSize();

  Color blockVersion();

  Color blockMerkleRoot();

  Color blockTime();

  Color blockBits();

  Color blockNonce();

  Color transactionHash();

  Color transactionConfirmedState();

  Color transactionConfirmations();

  Color transactionTime();

  Color transactionInputIndex();

  Color transactionScriptSigOpCode();

  Color transactionScriptSigPushData();

  Color transactionPubKeySigPushDataExtra();

  Color transactionInputSequence();

  Color transactionOutputAmount();

  Color transactionPubKeySigOpCode();

  Color transactionPubKeySigPushData();

  Color transactionVersion();

  Color transactionLockTime();

  Color transactionPubKeySigLength();

  Color transactionScriptSigLength();

  Color transactionOutputLength();

  Color transactionInputLength();

  Color transactionArbitraryData();

  Color stackData();

  Color stackSingle();

  Color address();

  Color addressHex();

  Color addressPayload();

  Color addressVersion();

  Color addressChecksum();

  Color addressValidity();

  Color addressAdverisedChecksum();

  Color addressComputedChecksum();

  Color addressOutpointSpent();

  Color addressOutpointUnspent();

  Color addressNumberOfTransactions();
}
