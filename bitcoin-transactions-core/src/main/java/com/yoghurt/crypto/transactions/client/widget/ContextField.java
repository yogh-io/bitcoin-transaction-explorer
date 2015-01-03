package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
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
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.util.ComputedStyle;

public class ContextField<T> extends Composite implements HasMouseOutHandlers, HasMouseOverHandlers, HasClickHandlers {
  interface ContextFieldUiBinder extends UiBinder<Widget, ContextField<?>> {}

  private static final ContextFieldUiBinder UI_BINDER = GWT.create(ContextFieldUiBinder.class);

  private static final int CLEAN_UP_DELAY = 5;

  /**
   * Needs to correspond to the same value in the UiBinder
   */
  private static final int ANIMATION_TIME = 200;

  @UiField CustomStyle style;

  @UiField FlowPanel container;

  private T value;

  private String currentText;

  private final Timer cleanupTimer = new Timer() {
    @Override
    public void run() {
      // Make sure we're not animating
      container.addStyleName(style.noAnimation());

      // Remove all but the last widget
      final Widget widg = container.getWidget(container.getWidgetCount() - 1);
      container.clear();
      container.add(widg);

      // Reset the 'top' attribute to 0, and enforce the DOM change
      container.getElement().getStyle().setTop(0, Unit.PX);
      ComputedStyle.getStyleProperty(container.getElement(), "top");

      // Re-enable animation
      container.removeStyleName(style.noAnimation());
    }
  };

  public interface CustomStyle extends CssResource {
    String fieldSelected();

    String fieldActive();

    String noAnimation();
  }

  public ContextField(final T value, final String text) {
    this.value = value;
    initWidget(UI_BINDER.createAndBindUi(this));

    setContent(text, false);
  }

  public void setContent(final String text) {
    setContent(text, true);
  }

  public void setContent(final String text, final boolean animate) {
    // Bug out if the text to display is the same as the current text
    if (text != null && text.equals(currentText)) {
      return;
    }

    currentText = text;

    if(!animate) {
      container.clear();
    }

    final Label lbl = new Label(text);
    container.add(lbl);

    if(animate) {
      container.getElement().getStyle().setTop(-container.getOffsetHeight() + lbl.getOffsetHeight(), Unit.PX);

      // This is gonna suck if there's more than a couple of these running, so thank god Mr Moore invented that law of his
      cleanupTimer.cancel();
      cleanupTimer.schedule(CLEAN_UP_DELAY + ANIMATION_TIME);
    }
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
