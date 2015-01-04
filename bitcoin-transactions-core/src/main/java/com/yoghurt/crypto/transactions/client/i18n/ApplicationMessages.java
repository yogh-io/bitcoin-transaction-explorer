package com.yoghurt.crypto.transactions.client.i18n;

import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.google.gwt.i18n.client.Messages;

@DefaultLocale("EN")
public interface ApplicationMessages extends Messages {

  @DefaultMessage("Blockchain reader")
  String applicationTitle();

  @DefaultMessage("For developers - Breaking it down to the bone")
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

  @DefaultMessage("Transaction error")
  String transactionPlaceTitleError();

  @DefaultMessage("Raw transaction in hex")
  String transactionPlaceTitleRawHex();

  @DefaultMessage("An error occurred while parsing the transaction, what you are seeing below is the best we could make out of the data before we stopped understanding it.")
  String transactionPlaceParseError();

  @DefaultMessage("Block")
  String blockPlaceTitle();

  @DefaultMessage("Block hash")
  String blockPlaceTitleInformation();

  @DefaultMessage("Presence in blockchain")
  String blockPlaceTitleExtraInformation();

  @DefaultMessage("Block headers")
  String blockPlaceTitleBlockHeaders();

  @DefaultMessage("Raw block in hex")
  String blockPlaceTitleRawHex();

  @DefaultMessage("Modify Headers")
  String minePlaceTitleModifyHeaders();

  @DefaultMessage("Headers to mine")
  String minePlaceTitleMineHeaders();

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

  @DefaultMessage("Sequence:")
  String transactionSequence();

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
}
