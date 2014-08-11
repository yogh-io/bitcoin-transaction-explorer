package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.widget.ContextFieldSet.FieldContextFactory;

public abstract class SimpleContextFactory<T> implements FieldContextFactory<T> {
  @Override
  public Widget getContextWidget(final T value) {
    final Label label = new Label(getTextValue(value));
    label.getElement().getStyle().setPadding(10, Unit.PX);
    return label;
  }

  protected abstract String getTextValue(T value);
}
