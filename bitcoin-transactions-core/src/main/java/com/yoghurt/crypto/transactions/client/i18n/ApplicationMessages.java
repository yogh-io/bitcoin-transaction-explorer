package com.yoghurt.crypto.transactions.client.i18n;

import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.google.gwt.i18n.client.Messages;

@DefaultLocale("EN")
public interface ApplicationMessages extends Messages {

  @DefaultMessage("Mining simulator")
  String applicationTitle();

  @DefaultMessage("Breaking it down to the bone")
  String applicationSubTitle();

  @DefaultMessage("V0.1 - 2015")
  String applicationVersion();

  @DefaultMessage("Insert anything, press enter")
  String applicationLookupFieldPlaceHolder();

  @DefaultMessage("Donate / contribute")
  String applicationContributionText();

  @DefaultMessage("Transaction")
  String transactionPlaceTitle();

  @DefaultMessage("Transaction hash")
  String transactionPlaceTitleInformation();

  @DefaultMessage("Presence in blockchain")
  String transactionPlaceTitleExtraInformation();

  @DefaultMessage("Transaction inputs")
  String transactionPlaceTitleTransactionInputs();

  @DefaultMessage("Transaction outputs")
  String transactionPlaceTitleTransactionOutputs();

  @DefaultMessage("Miscellaneous information")
  String transactionPlaceTitleMiscellaneousInformation();

  @DefaultMessage("Error")
  String transactionPlaceTitleError();

  @DefaultMessage("Raw transaction in hex")
  String transactionPlaceTitleRawHex();

  @DefaultMessage("An error occurred while parsing the transaction, what you are seeing below is the best we could make out of the data before we stopped understanding it.")
  String transactionPlaceParseError();

  @DefaultMessage("Block")
  String blockPlaceTitle();

  @DefaultMessage("Block hash")
  String blockPlaceTitleInformation();

  @DefaultMessage("Coinbase transaction hash")
  String blockPlaceTitleCoinbase();

  @DefaultMessage("Presence in blockchain")
  String blockPlaceTitleExtraInformation();

  @DefaultMessage("Block headers")
  String blockPlaceTitleBlockHeaders();

  @DefaultMessage("Raw block in hex")
  String blockPlaceTitleRawBlockHex();

  @DefaultMessage("Raw coinbase transaction in hex")
  String blockPlaceTitleRawCoinbaseHex();

  @DefaultMessage("Mining simulator")
  String minePlaceTitleMiningSimulator();

  @DefaultMessage("Modify Headers")
  String minePlaceTitleModifyHeaders();

  @DefaultMessage("Headers to mine")
  String minePlaceTitleMineHeaders();

  @DefaultMessage("Coinbase transaction")
  String minePlaceTitleCoinbase();

  @DefaultMessage("Computed block hash")
  String minePlaceTitleBlockHash();

  @DefaultMessage("Block headers")
  String minePlaceTitleBlockHeaders();

  @DefaultMessage("Transaction ID:")
  String transactionId();

  @DefaultMessage("Previous tx:")
  String transactionPreviousTransactionId();

  @DefaultMessage("Index:")
  String transactionOutpointIndex();

  @DefaultMessage("ScriptSig:")
  String transactionScriptSig();

  @DefaultMessage("ScriptPubKey:")
  String transactionScriptPubKey();

  @DefaultMessage("View in script viewer")
  String transactionScriptViewer();

  @DefaultMessage("Sequence:")
  String transactionSequence();

  @DefaultMessage("Amount:")
  String transactionAmount();

  @DefaultMessage("State:")
  String transactionState();

  @DefaultMessage("Time:")
  String transactionTime();

  @DefaultMessage("Format version:")
  String transactionVersion();

  @DefaultMessage("Lock time:")
  String transactionLockTime();

  @DefaultMessage("Checking existence in the blockchain ...")
  String transactionPlaceBlockchainExistenceLoading();

  @DefaultMessage("This transaction does not exist in the blockchain and could not be found in the unconfirmed transaction pool.")
  String transactionPlaceBlockchainExistenceNotFound();

  @DefaultMessage("Block height:")
  String blockHeight();

  @DefaultMessage("Block hash:")
  String blockHash();

  @DefaultMessage("Confirmations:")
  String blockConfirmations();

  @DefaultMessage("Version:")
  String blockVersion();

  @DefaultMessage("Prev block:")
  String blockPreviousBlock();

  @DefaultMessage("Merkle root:")
  String blockMerkleRoot();

  @DefaultMessage("Timestamp")
  String blockTime();

  @DefaultMessage("Bits:")
  String blockBits();

  @DefaultMessage("Nonce:")
  String blockNonce();

  @DefaultMessage("Block depth:")
  String blockDepth();

  @DefaultMessage("# transactions:")
  String blockNumTransactions();

  @DefaultMessage("Next block:")
  String blockNextBlock();

  @DefaultMessage("Size (bytes):")
  String blockSize();

  @DefaultMessage("Checking existence in the blockchain ...")
  String blockPlaceBlockchainExistenceLoading();

  @DefaultMessage("This block could not be found in the blockchain.")
  String blockPlaceBlockchainExistenceNotFound();

  @DefaultMessage("Script viewer")
  String scriptPlaceTitleScriptViewer();

  @DefaultMessage("Outpoint information")
  String scriptPlaceTitleOutpointViewer();

  @DefaultMessage("Tx hash:")
  String scriptPlaceOutpointHash();

  @DefaultMessage("Scripts")
  String scriptPlaceTitleScripts();

  @DefaultMessage("ScriptSig in raw hex")
  String scriptPlaceScriptSigRawHex();

  @DefaultMessage("ScriptPubKey in raw hex")
  String scriptPlaceScriptPubKeyRawHex();

  @DefaultMessage("Script execution, step-by-step")
  String scriptPlaceStepByStep();

  @DefaultMessage("Operation:")
  String scriptOperation();

  @DefaultMessage("Result:")
  String scriptResult();

  @DefaultMessage("Remaining:")
  String scriptRemainingScript();

  @DefaultMessage("Execution step {0}")
  String scriptExecutionStep(int idx);
}
