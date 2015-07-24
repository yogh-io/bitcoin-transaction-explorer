package com.yoghurt.crypto.transactions.client.widget;

import java.util.ArrayList;

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
import com.yoghurt.crypto.transactions.client.resources.R;
import com.yoghurt.crypto.transactions.client.util.misc.Color;

public class ContextFieldSet<T> extends FlowPanel {
  private static final int POPUP_SHOW_DELAY = 50;
  private static final int POPUP_HIDE_DELAY = 70;

  protected ArrayList<ContextField<T>> fields = new ArrayList<ContextField<T>>();

  public interface FieldContextFactory<T> {
    Widget getContextWidget(T part);
  }

  private final class TypedTimer extends Timer {
    private final ContextField<T> source;

    public TypedTimer(final ContextField<T> source) {
      this.source = source;
    }

    @Override
    public void run() {
      if (selectedField == null || selectedField == source) {
        displayContextPopup(source);
      }
    }
  }

  private final Timer popupHideTimer = new Timer() {
    @Override
    public void run() {
      clearActivity();
    }
  };

  private final MouseOverHandler mouseOverHandler = new MouseOverHandler() {
    @SuppressWarnings("unchecked")
    @Override
    public void onMouseOver(final MouseOverEvent event) {
      popupHideTimer.cancel();

      if (popupShowTimer != null) {
        popupShowTimer.cancel();
      }

      popupShowTimer = new TypedTimer((ContextField<T>) event.getSource());
      popupShowTimer.schedule(popupPanel.isShowing() ? 0 : POPUP_SHOW_DELAY);
    }
  };

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

  public ContextFieldSet() {}

  public ContextFieldSet(final FieldContextFactory<T> contextFactory) {
    this.contextFactory = contextFactory;

    setStyleName(R.css().flex());
  }

  public ContextFieldSet(final String text) {
    this(new TextContextFactory<T>(text));
  }

  @Override
  public void clear() {
    super.clear();
    fields.clear();
    clearActivity();
  }

  protected void clearActivity() {
    selectedField = null;
    popupPanel.hide();
  }

  public void addField(final T value) {
    addField(value, getFieldColor(value));
  }

  public void addField(final T value, final Color color) {
    addField(value, color, getFieldText(value));
  }

  public ContextField<T> addField(final T value, final Color color, final String text) {
    final ContextField<T> field = createContextField(value, color, text);

    field.addMouseOverHandler(mouseOverHandler);
    field.addMouseOutHandler(mouseOutHandler);
    field.addClickHandler(mouseClickHandler);

    fields.add(field);
    return addField(field);
  }

  protected ContextField<T> createContextField(final T value, final Color color, final String text) {
    return new ContextField<T>(value, color, text);
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

  protected void removeField(final int counter) {
    fields.remove(counter);
    remove(counter);
  }

  private void delayedHide() {
    popupHideTimer.schedule(POPUP_HIDE_DELAY);
  }

  // TODO delegate context styling out of here
  private void displayContextPopup(final ContextField<T> field) {
    if (contextFactory == null) {
      return;
    }

    // Create a new content widget
    final Widget popupContent = contextFactory.getContextWidget(field.getValue());

    final ContextPanel panel = new ContextPanel();
    panel.setWidget(popupContent);

    final Color borderColor = getFieldColor(field.getValue()).copy();
    final Color backgroundColor = borderColor.copy();
    borderColor.setA(0.8);
    backgroundColor.setA(0.01);

    panel.getElement().getStyle().setBorderColor(borderColor.getValue());
    popupContent.getElement().getStyle().setBackgroundColor(backgroundColor.getValue());

    // Notify display
    displayContextForValue(field.getValue());

    // Display the popup
    displayContextPopup(field, panel);
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
