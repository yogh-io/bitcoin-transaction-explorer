package com.yoghurt.crypto.transactions.client.i18n;

import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.google.gwt.i18n.client.Messages;
import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;
import com.yoghurt.crypto.transactions.shared.domain.Operation;

@DefaultLocale("EN")
public interface ApplicationMessages extends Messages {

  @Description("v0.1 - alpha - april 2015")
  String applicationVersion();

  @Description("Insert anything, press enter")
  String applicationLookupFieldPlaceHolder();

  @Description("Donate / contribute")
  String applicationContributionText();

  @Description("General navigation")
  String startupGeneral();

  @Description("Last block")
  String startupLastBlock();

  @Description("Address")
  String addressPlaceTitle();

  @Description("Base58Check breakdown")
  String addressPlaceBase58CheckTitle();

  @Description("Finance")
  String addressPlaceFinance();

  @Description("QR Code")
  String addressPlaceQRCode();

  @Description("Hash160:")
  String addressHash160();

  @Description("Checksum:")
  String addressChecksum();

  @Description("Version:")
  String addressVersion();

  @Description("Address:")
  String addressAddress();

  @Description("Address (hex):")
  String addressAddressHex();

  @Description("Well formed:")
  String addressValidFormat();

  @Description("Address malformed.")
  String addressMalformedText();

  @Description("Computed checksum")
  String addressComputedChecksum();

  @Description("Advertised checksum")
  String addressAdvertisedChecksum();

  @Description("Do not send money here!")
  String addressMalformedWarning();

  @Description("Spent:")
  String addressOutpointSpent();

  @Description("# Transactions:")
  String addressNumberOfTransactions();

  @Description("Balance:")
  String addressBalance();

  @Description("Transaction")
  String transactionPlaceTitle();

  @Description("Transaction hash")
  String transactionPlaceTitleInformation();

  @Description("Presence in blockchain")
  String transactionPlaceTitleExtraInformation();

  @Description("Transaction inputs")
  String transactionPlaceTitleTransactionInputs();

  @Description("Transaction outputs")
  String transactionPlaceTitleTransactionOutputs();

  @Description("Miscellaneous information")
  String transactionPlaceTitleMiscellaneousInformation();

  @Description("Error")
  String transactionPlaceTitleError();

  @Description("Raw transaction in hex")
  String transactionPlaceTitleRawHex();

  @Description("An error occurred while parsing the transaction, what you are seeing below is the best we could make out of the data before we stopped understanding it.")
  String transactionPlaceParseError();

  @Description("Block")
  String blockPlaceTitle();

  @Description("Block hash")
  String blockPlaceTitleInformation();

  @Description("Coinbase transaction hash")
  String blockPlaceTitleCoinbase();

  @Description("Presence in blockchain")
  String blockPlaceTitleExtraInformation();

  @Description("Block headers")
  String blockPlaceTitleBlockHeaders();

  @Description("Raw block headers in hex")
  String blockPlaceTitleRawBlockHex();

  @Description("Raw coinbase transaction in hex")
  String blockPlaceTitleRawCoinbaseHex();

  @Description("Transaction list")
  String blockPlaceTitleTransactionList();

  @Description("Mining simulator")
  String minePlaceTitleMiningSimulator();

  @Description("Modify Headers")
  String minePlaceTitleModifyHeaders();

  @Description("Raw headers in hex")
  String minePlaceTitleMineHeaders();

  @Description("Coinbase transaction")
  String minePlaceTitleCoinbase();

  @Description("Computed block hash")
  String minePlaceTitleBlockHash();

  @Description("Block headers")
  String minePlaceTitleBlockHeaders();

  @Description("Transaction ID:")
  String transactionId();

  @Description("Previous tx:")
  String transactionPreviousTransactionId();

  @Description("Index:")
  String transactionOutpointIndex();

  @Description("ScriptSig:")
  String transactionScriptSig();

  @Description("ScriptPubKey:")
  String transactionScriptPubKey();

  @Description("ASCII interpret:")
  String transactionCoinbaseInput();

  @Description("View in script viewer")
  String transactionScriptViewer();

  @Description("Sequence:")
  String transactionSequence();

  @Description("Amount:")
  String transactionAmount();

  @Description("State:")
  String transactionState();

  @Description("Derived address:")
  String transactionOutputAddress();

  @Description("Time:")
  String transactionTime();

  @Description("Format version:")
  String transactionVersion();

  @Description("Lock time:")
  String transactionLockTime();

  @Description("Checking existence in the blockchain ...")
  String transactionPlaceBlockchainExistenceLoading();

  @Description("This transaction does not exist in the blockchain and could not be found in the unconfirmed transaction pool.")
  String transactionPlaceBlockchainExistenceNotFound();

  @Description("Block height:")
  String blockHeight();

  @Description("Block hash:")
  String blockHash();

  @Description("Confirmations:")
  String blockConfirmations();

  @Description("Version:")
  String blockVersion();

  @Description("Prev block:")
  String blockPreviousBlock();

  @Description("Merkle root:")
  String blockMerkleRoot();

  @Description("Timestamp")
  String blockTime();

  @Description("Bits:")
  String blockBits();

  @Description("Bits target explanation.")
  String blockBitsTargetExplain();

  @Description("Nonce:")
  String blockNonce();

  @Description("Block depth:")
  String blockDepth();

  @Description("# transactions:")
  String blockNumTransactions();

  @Description("Next block:")
  String blockNextBlock();

  @Description("Size (bytes):")
  String blockSize();

  @Description("Checking existence in the blockchain ...")
  String blockPlaceBlockchainExistenceLoading();

  @Description("This block could not be found in the blockchain.")
  String blockPlaceBlockchainExistenceNotFound();

  @Description("Script viewer")
  String scriptPlaceTitleScriptViewer();

  @Description("Outpoint information")
  String scriptPlaceTitleOutpointViewer();

  @Description("Tx hash:")
  String scriptPlaceOutpointHash();

  @Description("Scripts")
  String scriptPlaceTitleScripts();

  @Description("Execution result")
  String scriptPlaceExecutionResult();

  @Description("Script execution was succesful.")
  String scriptPlaceExecutionResultSuccess();

  @Description("Script execution was unsuccesful due to a failure trigger.")
  String scriptPlaceExecutionResultFailureTriggered();

  @Description("Script execution was succesful but the resulting stack makes the transaction invalid.")
  String scriptPlaceExecutionResultFailureResult();

  @Description("ScriptSig in raw hex")
  String scriptPlaceScriptSigRawHex();

  @Description("ScriptPubKey in raw hex")
  String scriptPlaceScriptPubKeyRawHex();

  @Description("Script execution, step-by-step")
  String scriptPlaceStepByStep();

  @Description("Operation:")
  String scriptOperation();

  @Description("Description:")
  String scriptOperationDescriptionLabel();

  @Description("Result:")
  String scriptResult();

  @Description("Remaining:")
  String scriptRemainingScript();

  @Description("Resulting stack:")
  String scriptResultStack();

  @Description("Execution step {0}")
  String scriptExecutionStep(int idx);

  @Description("Configuration")
  String configPlaceTitle();

  @Description("You haven''t set up a password for the application yet. Please insert a password and remember it, you''ll need it for any subsequent modifications to the configuration (although you''d typically only configure it once).<br><br>If you''ve forgotten your password you''ll need to reset (remove) it manually, its hash is stored in the file ''yoghurt.conf''.<br/><br/>The server will never store your plain-text password, it will be hashed on the client and then sent in clear text over the wire.")
  String configPlaceCreatePasswordText();

  @Description("Password")
  String configPasswordPlaceHolder();

  @Description("Password confirmation")
  String configPasswordRepeatPlaceHolder();

  @Description("Set password")
  String configPasswordConfirm();

  @Description("Log in.")
  String configPasswordLogin();

  @Description("Insert your password.")
  String configPlaceInputPasswordText();

  @Description("Connector:")
  String configBlockchainHook();

  @Description("Save settings")
  String configSubmitSettings();

  @Description("General settings")
  String configMiscellaneousSettingsTitle();

  @Description("Blockchain source settings")
  String configBlockchainHookTitle();

  @Description("Unknown (not supported)")
  @AlternateMessage({
    "NODE", "Bitcoin Core Node",
    "BLOCKR_API", "BLOCKR API"
  })
  String configConnectorOption(@Select BlockchainSource source);

  @Description("No further config needed, press save to continue.")
  String configBlockrConfigNote();

  @Description("Host (probably localhost)")
  String configNodeHost();

  @Description("Port (probably 8332)")
  String configNodePort();

  @Description("RPC Username")
  String configNodeRpcUser();

  @Description("RPC Password")
  String configNodeRpcPass();

  @Description("Application title")
  String configTitlePlaceHolder();

  @Description("Application subtitle")
  String configSubTitlePlaceHolder();

  @Description("Donation address")
  String configContributeAddressPlaceHolder();

  @Description("Application texts")
  String configTitles();

  @Description("Node contribution")
  String configContribution();

  @Description("Script operation description")
  String scriptOperationDescription(@Select Operation operation);

  @Description("Full script:")
  String scriptPlaceFullScript();

  @Description("Contribute to this project")
  String contributePlaceTitle();

  @Description("Contribute to project text")
  String contributePlaceText(String donationAddress);

  @Description("Donate to this node")
  String contributePlaceNodeTitle();

  @Description("Donate to node text")
  String contributePlaceNodeText(String donationAddress);

  @Description("JSON Response view")
  String jsonPlaceTitle();
}
