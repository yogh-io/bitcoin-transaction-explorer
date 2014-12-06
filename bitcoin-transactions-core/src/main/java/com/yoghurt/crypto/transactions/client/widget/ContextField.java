package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ContextField<T> extends Composite implements HasMouseOutHandlers, HasMouseOverHandlers, HasClickHandlers {
  interface ContextFieldUiBinder extends UiBinder<Widget, ContextField<?>> {}

  private static final ContextFieldUiBinder UI_BINDER = GWT.create(ContextFieldUiBinder.class);

  @UiField CustomStyle style;

  @UiField Label field;

  private T value;

  public interface CustomStyle extends CssResource {
    String fieldSelected();

    String fieldActive();
  }
  public ContextField(final T value, final String text) {
    this.value = value;
    initWidget(UI_BINDER.createAndBindUi(this));

    field.setText(text);
  }

  public void setContent(final String content) {
    field.setText(content);
  }

  public T getValue() {
    return value;
  }

  public void setValue(final T value) {
    this.value = value;
  }

  public void setSelected(final boolean selected) {
    setStyleName(style.fieldSelected(), selected);
  }

  public void setActive(final boolean active) {
    setStyleName(style.fieldActive(), active);
  }

  @Override
  public HandlerRegistration addClickHandler(final ClickHandler handler) {
    return addDomHandler(handler, ClickEvent.getType());
  }

  @Override
  public HandlerRegistration addMouseOverHandler(final MouseOverHandler handler) {
    return addDomHandler(handler, MouseOverEvent.getType());
  }

  @Override
  public HandlerRegistration addMouseOutHandler(final MouseOutHandler handler) {
    return addDomHandler(handler, MouseOutEvent.getType());
  }
}
