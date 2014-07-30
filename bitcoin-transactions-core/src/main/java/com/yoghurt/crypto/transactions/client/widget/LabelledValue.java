package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class LabelledValue extends FlowPanel implements LeafValueEditor<String> {
  private final Label labelField = new Label();
  private final Label valueField = new Label();

  public LabelledValue() {
    add(labelField);
    add(valueField);
  }

  @Override
  public String getValue() {
    return valueField.getText();
  }

  @Override
  public void setValue(final String value) {
    valueField.setText(value);
  }

  public void setLabel(final String label) {
    labelField.setText(label);
  }

  public void setLabelStyle(final String style) {
    labelField.setStyleName(style);
  }

  public void setValueStyle(final String style) {
    valueField.setStyleName(style);
  }
}
