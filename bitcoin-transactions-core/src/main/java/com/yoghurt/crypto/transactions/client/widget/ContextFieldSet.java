package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.util.misc.Color;

public class ContextFieldSet<T> extends FlowPanel {
  private static final int POPUP_SHOW_DELAY = 150;
  private static final int POPUP_HIDE_DELAY = 200;

  public interface FieldContextFactory<T> {
    Widget getContextWidget(T part);
  }

  private final class TypedTimer extends Timer {
    private final T valueContainer;
    private final Object source;

    public TypedTimer(final T valueContainer, final Object source) {
      this.source = source;
      this.valueContainer = valueContainer;
    }

    @Override
    public void run() {
      if (selectedField == null || selectedField == source) {
        displayContextPopup((Widget) source, valueContainer);
      }
    }
  }

  private final Timer popupHideTimer = new Timer() {
    @Override
    public void run() {
      popupPanel.hide();
    }
  };

  private final class ContextHandler implements MouseOverHandler {
    private final T valueContainer;

    public ContextHandler(final T valueContainer) {
      this.valueContainer = valueContainer;
    }

    @Override
    public void onMouseOver(final MouseOverEvent event) {
      popupHideTimer.cancel();

      if (popupShowTimer != null) {
        popupShowTimer.cancel();
      }

      popupShowTimer = new TypedTimer(valueContainer, event.getSource());
      popupShowTimer.schedule(popupPanel.isShowing() ? 0 : POPUP_SHOW_DELAY);
    }
  }

  private final MouseOutHandler mouseOutHandler = new MouseOutHandler() {
    @Override
    public void onMouseOut(final MouseOutEvent event) {
      if (selectedField == null) {
        popupShowTimer.cancel();
        delayedHide();
      }
    }
  };

  private ClickHandler mouseClickHandler = new ClickHandler() {
    @SuppressWarnings("unchecked")
    @Override
    public void onClick(final ClickEvent event) {
      if (selectedField != null) {
        selectedField.setSelected(false);
      }

      if (selectedField == event.getSource()) {
        selectedField = null;
        return;
      }

      selectedField = (ContextField<T>) event.getSource();
      selectedField.setSelected(true);

      selectedField.fireEvent(new MouseOverEvent() {});
    }
  };

  protected FieldContextFactory<T> contextFactory;

  private final AttachedContextPanel popupPanel = new AttachedContextPanel();
  private Timer popupShowTimer;

  private ContextField<T> selectedField;
  private final AttachEvent.Handler attachHandler = new AttachEvent.Handler() {

    @Override
    public void onAttachOrDetach(final AttachEvent event) {
      if (!event.isAttached()) {
        popupPanel.hide();
        if (attachRegistration != null) {
          attachRegistration.removeHandler();
        }
      }
    }

  };
  private HandlerRegistration attachRegistration;

  public ContextFieldSet(final FieldContextFactory<T> contextFactory) {
    this.contextFactory = contextFactory;
  }

  @Override
  public void clear() {
    super.clear();
    popupPanel.hide();
    selectedField = null;
  }

  public FieldContextFactory<T> getContextFactory() {
    return contextFactory;
  }

  public void setContextFactory(final FieldContextFactory<T> contextFactory) {
    this.contextFactory = contextFactory;
  }

  public void addField(final T value) {
    addField(value, getFieldColor(value));
  }

  public void addField(final T value, final Color color) {
    addField(value, color, getFieldText(value));
  }

  public ContextField<T> addField(final T value, final Color color, final String text) {
    final ContextField<T> field = new ContextField<T>(text);

    field.addMouseOverHandler(new ContextHandler(value));
    field.addMouseOutHandler(mouseOutHandler);
    field.addClickHandler(mouseClickHandler);

    final Color backgroundColor = color.copy();
    backgroundColor.setA(0.2);

    field.getElement().getStyle().setBorderColor(color.getValue());
    field.getElement().getStyle().setBackgroundColor(backgroundColor.getValue());

    return addField(field);
  }

  protected Color getFieldColor(final T value) {
    // No-op by default
    return null;
  }

  protected String getFieldText(final T value) {
    // No-op by default
    return null;
  }

  protected ContextField<T> addField(final ContextField<T> field) {
    add(field);

    return field;
  }

  private void delayedHide() {
    popupHideTimer.schedule(POPUP_HIDE_DELAY);
  }

  // TODO delegate context styling out of here
  private void displayContextPopup(final Widget target, final T value) {
    // Create a new content widget
    final Widget popupContent = contextFactory.getContextWidget(value);

    final ContextPanel panel = new ContextPanel();
    panel.setWidget(popupContent);

    final Color borderColor = getFieldColor(value);
    final Color backgroundColor = borderColor.copy();
    borderColor.setA(0.9);
    backgroundColor.setA(0.02);

    panel.getElement().getStyle().setBorderColor(borderColor.getValue());
    popupContent.getElement().getStyle().setBackgroundColor(backgroundColor.getValue());

    // Notify display
    displayContextForValue(value);

    // Display the popup
    displayContextPopup(target, panel);
  }

  protected void displayContextForValue(final T value) {
    // No-op by default
  }

  private void displayContextPopup(final Widget target, final Widget popupContent) {
    if (!popupPanel.isShowing()) {
      popupContent.getElement().getStyle().setVisibility(Visibility.HIDDEN);
    }

    popupPanel.setWidget(popupContent);
    popupPanel.show();
    attachRegistration = target.addAttachHandler(attachHandler);

    // Defer the attach event because we don't have the element's width/height at this point
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        popupPanel.attachToWidget(target);
        popupContent.getElement().getStyle().clearVisibility();
      }
    });
  }

  public void setMouseClickHandler(final ClickHandler mouseClickHandler) {
    this.mouseClickHandler = mouseClickHandler;
  }
}
