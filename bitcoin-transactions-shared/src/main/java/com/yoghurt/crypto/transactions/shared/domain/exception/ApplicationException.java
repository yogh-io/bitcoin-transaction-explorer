package com.yoghurt.crypto.transactions.shared.domain.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ApplicationException extends Exception implements IsSerializable {
  private static final long serialVersionUID = 6580816542322401116L;

  private Reason reason;

  public enum Reason {
    INTERNAL_ERROR,

    ILLEGAL_OPERATION,

    UNSUPPORTED_OPERATION;
  }

  public ApplicationException() {
    super();
  }

  public ApplicationException(final String message) {
    super(message);
  }

  public ApplicationException(final Reason reason) {
    super();

    this.setReason(reason);
  }

  public Reason getReason() {
    return reason;
  }

  public void setReason(final Reason reason) {
    this.reason = reason;
  }
}
