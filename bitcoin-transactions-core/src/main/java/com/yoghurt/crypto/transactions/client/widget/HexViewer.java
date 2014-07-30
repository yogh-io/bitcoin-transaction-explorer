package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;

public abstract class HexViewer<T> extends Composite {
  interface HexViewerUiBinder extends UiBinder<Widget, HexViewer<?>> {}

  private static final HexViewerUiBinder UI_BINDER = GWT.create(HexViewerUiBinder.class);

  public interface CustomStyle extends CssResource {
    String byteField();
  }

  @UiField FlowPanel fieldPanel;

  @UiField CustomStyle style;

  public HexViewer() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  public void addField(final byte bite, final T type) {
    final Label field = new Label(new String(Hex.encode(new byte[] { bite })));
    field.setStyleName(style.byteField());

    final String typeColor = getColorForType(type);

    field.getElement().getStyle().setBorderColor(typeColor);
    fieldPanel.add(field);
  }

  protected abstract String getColorForType(final T type);

  public void clear() {
    fieldPanel.clear();
  }
}
