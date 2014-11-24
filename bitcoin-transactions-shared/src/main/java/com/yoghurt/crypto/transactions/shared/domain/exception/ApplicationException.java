package com.yoghurt.crypto.transactions.shared.domain.exception;

public class ApplicationException extends Exception {
  private static final long serialVersionUID = 6580816542322401116L;

  public ApplicationException() {
    super();
  }

  public ApplicationException(final String message) {
    super(message);
  }
}
