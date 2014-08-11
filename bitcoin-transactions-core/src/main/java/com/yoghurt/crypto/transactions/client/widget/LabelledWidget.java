package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LabelledWidget extends SimplePanel {
  private final FlowPanel container = new FlowPanel();

  private final Label label = new Label();

  public LabelledWidget() {
    super.setWidget(container);

    container.add(label);
  }

  public void setLabel(final String txt) {
    label.setText(txt);
  }

  public void setLabelStyleName(final String styleName) {
    label.setStyleName(styleName);
  }

  @Override
  public void add(final Widget w) {
    if(container.getWidgetCount() == 2) {
      container.remove(1);
    }

    container.add(w);
  }

  @Override
  public void add(final IsWidget w) {
    add(w.asWidget());
  }
}
