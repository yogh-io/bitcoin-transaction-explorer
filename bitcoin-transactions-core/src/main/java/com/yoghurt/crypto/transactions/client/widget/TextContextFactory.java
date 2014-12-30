package com.yoghurt.crypto.transactions.client.widget;

public class TextContextFactory<T> extends SimpleContextFactory<T> {
  private final String text;

  public TextContextFactory(final String text) {
    this.text = text;
  }

  @Override
  protected String getTextValue(final T value) {
    return text;
  }
}
