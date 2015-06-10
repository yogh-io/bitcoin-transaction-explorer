package com.yoghurt.crypto.transactions.client.i18n;

import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.google.gwt.i18n.client.Messages;
import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;

@DefaultLocale("EN")
public interface ApplicationMessages extends Messages {

  @DefaultMessage("v0.1 - alpha - april 2015")
  String applicationVersion();

  @DefaultMessage("Insert anything, press enter")
  String applicationLookupFieldPlaceHolder();

  @DefaultMessage("Donate / contribute")
  String applicationContributionText();

  @DefaultMessage("General navigation")
  String startupGeneral();

  @DefaultMessage("Last block")
  String startupLastBlock();

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

  @DefaultMessage("Raw block headers in hex")
  String blockPlaceTitleRawBlockHex();

  @DefaultMessage("Raw coinbase transaction in hex")
  String blockPlaceTitleRawCoinbaseHex();

  @DefaultMessage("Mining simulator")
  String minePlaceTitleMiningSimulator();

  @DefaultMessage("Modify Headers")
  String minePlaceTitleModifyHeaders();

  @DefaultMessage("Raw headers in hex")
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

  @DefaultMessage("Tx fee:")
  String transactionFee();

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

  @DefaultMessage("Configuration")
  String configPlaceTitle();

  @DefaultMessage("You haven''t set up a password for the application yet. Please insert a password and remember it, you''ll need it for any subsequent modifications to the configuration (although you''d typically only configure it once).<br><br>If you''ve forgotten your password you''ll need to reset (remove) it manually, its hash is stored in the file ''yoghurt.conf''.<br/><br/>The server will never store your plain-text password, it will be hashed on the client and then sent in clear text over the wire.")
  String configPlaceCreatePasswordText();

  @DefaultMessage("Password")
  String configPasswordPlaceHolder();

  @DefaultMessage("Password confirmation")
  String configPasswordRepeatPlaceHolder();

  @DefaultMessage("Set password")
  String configPasswordConfirm();

  @DefaultMessage("Log in.")
  String configPasswordLogin();

  @DefaultMessage("Insert your password.")
  String configPlaceInputPasswordText();

  @DefaultMessage("Connector:")
  String configBlockchainHook();

  @DefaultMessage("Save settings")
  String configSubmitSettings();

  @DefaultMessage("General settings")
  String configMiscellaneousSettingsTitle();

  @DefaultMessage("Blockchain source settings")
  String configBlockchainHookTitle();

  @DefaultMessage("Unknown (not supported)")
  @AlternateMessage({
    "NODE", "Bitcoin Core Node",
    "BLOCKR_API", "BLOCKR API"
  })
  String configConnectorOption(@Select BlockchainSource source);

  @DefaultMessage("No further config needed, press save to continue.")
  String configBlockrConfigNote();

  @DefaultMessage("Host (probably localhost)")
  String configNodeHost();

  @DefaultMessage("Port (probably 8332)")
  String configNodePort();

  @DefaultMessage("RPC Username")
  String configNodeRpcUser();

  @DefaultMessage("RPC Password")
  String configNodeRpcPass();

  @DefaultMessage("Application title")
  String configTitlePlaceHolder();

  @DefaultMessage("Application subtitle")
  String configSubTitlePlaceHolder();

  @DefaultMessage("Donation address")
  String configContributeAddressPlaceHolder();

  @DefaultMessage("Application texts")
  String configTitles();

  @DefaultMessage("Node contribution")
  String configContribution();
}
