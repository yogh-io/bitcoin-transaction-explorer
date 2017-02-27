package com.yoghurt.crypto.transactions.shared.service.domain.exception;

public class ApplicationException extends Exception {
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
