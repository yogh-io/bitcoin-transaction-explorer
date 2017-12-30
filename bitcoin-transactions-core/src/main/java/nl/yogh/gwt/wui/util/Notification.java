package nl.yogh.gwt.wui.util;

public class Notification {
  private boolean error;
  private String message;

  public Notification(final boolean error, final String message) {
    this.error = error;
    this.message = message;
  }

  public boolean isError() {
    return error;
  }

  public void setError(final boolean error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Notification [error=" + error + ", message=" + message + "]";
  }
}