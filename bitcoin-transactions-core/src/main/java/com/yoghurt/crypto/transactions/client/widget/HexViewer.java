package com.yoghurt.crypto.transactions.client.widget;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.util.Color;

public abstract class HexViewer<T> extends Composite {
  private static final int POPUP_SHOW_DELAY = 150;
  private static final int POPUP_HIDE_DELAY = 200;

  private final class TypedTimer extends Timer {
    private final T valueContainer;
    private final Object source;

    public TypedTimer(final T valueContainer, final Object source) {
      this.source = source;
      this.valueContainer = valueContainer;
    }

    @Override
    public void run() {
      if(selectedField == null || selectedField == source) {
        displayContextPopup((Widget) source, valueContainer);
      }
    }
  }

  private final class ContextHandler implements MouseOverHandler {
    private final T valueContainer;

    public ContextHandler(final T valueContainer) {
      this.valueContainer = valueContainer;
    }

    @Override
    public void onMouseOver(final MouseOverEvent event) {
      popupHideTimer.cancel();

      if(popupShowTimer != null) {
        popupShowTimer.cancel();
      }

      popupShowTimer = new TypedTimer(valueContainer, event.getSource());
      popupShowTimer.schedule(popupPanel.isShowing() ? 0 : POPUP_SHOW_DELAY);
    }
  }

  interface HexViewerUiBinder extends UiBinder<Widget, HexViewer<?>> {}

  private static final HexViewerUiBinder UI_BINDER = GWT.create(HexViewerUiBinder.class);

  public interface CustomStyle extends CssResource {
    String byteField();

    String byteSet();

    String fieldSelected();
  }

  @UiField FlowPanel fieldPanel;

  @UiField CustomStyle style;

  private final AttachedContextPanel popupPanel = new AttachedContextPanel();

  private final HashMap<T, Widget> contextPopups = new HashMap<T, Widget>();

  private FlowPanel byteSetContainer;

  private Widget selectedField;

  private Timer popupShowTimer;
  private final Timer popupHideTimer = new Timer() {
    @Override
    public void run() {
      popupPanel.hide();
    }
  };

  private final MouseOutHandler mouseOutHandler = new MouseOutHandler() {
    @Override
    public void onMouseOut(final MouseOutEvent event) {
      if(selectedField == null) {
        popupShowTimer.cancel();
        delayedHide();
      }
    }
  };

  private final ClickHandler mouseClickHandler = new ClickHandler() {
    @Override
    public void onClick(final ClickEvent event) {
      if(selectedField != null) {
        selectedField.removeStyleName(style.fieldSelected());
      }

      if(selectedField == event.getSource()) {
        selectedField = null;
        return;
      }

      selectedField = (Widget)event.getSource();
      selectedField.addStyleName(style.fieldSelected());

      selectedField.fireEvent(new MouseOverEvent(){});
    }
  };

  public HexViewer() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  public void addFields(final T value) {
    final Color typeColor = getColorForValue(value);

    for (final byte bite : getBytesForvalue(value)) {
      addField(bite, typeColor, value);
    }
  }

  protected abstract byte[] getBytesForvalue(final T value);

  private void delayedHide() {
    popupHideTimer.schedule(POPUP_HIDE_DELAY);
  }

  private void displayContextPopup(final Widget target, final T valueContainer) {
    // If the content is cached, get and display it
    if (contextPopups.containsKey(valueContainer)) {
      displayContextPopup(target, contextPopups.get(valueContainer));
      return;
    }

    // Create and save a new content widget
    final Widget popupContent = createContextPopupContent(valueContainer);

    final HexContextPanel panel = new HexContextPanel();
    panel.setWidget(popupContent);

    final Color borderColor = getColorForValue(valueContainer);
    final Color backgroundColor = borderColor.copy();
    borderColor.setA(0.8);
    backgroundColor.setA(0.02);

    panel.getElement().getStyle().setBorderColor(borderColor.getValue());
    popupContent.getElement().getStyle().setBackgroundColor(backgroundColor.getValue());

    contextPopups.put(valueContainer, panel);

    // Display the popup
    displayContextPopup(target, panel);
  }

  private void displayContextPopup(final Widget target, final Widget popupContent) {
    if(!popupPanel.isShowing()) {
      popupContent.getElement().getStyle().setVisibility(Visibility.HIDDEN);
    }

    popupPanel.setWidget(popupContent);
    popupPanel.show();

    // Defer the attach event because we don't have the element's width/height at this point
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        popupPanel.attachToWidget(target);
        popupContent.getElement().getStyle().clearVisibility();
      }
    });
  }

  private void addField(final byte bite, final Color typeColor, final T container) {
    final Label field = new Label(new String(Hex.encode(new byte[] { bite })));
    field.setStyleName(style.byteField());

    final Color backgroundColor = typeColor.copy();
    backgroundColor.setA(0.2);

    field.addMouseOverHandler(new ContextHandler(container));
    field.addMouseOutHandler(mouseOutHandler);
    field.addClickHandler(mouseClickHandler);

    field.getElement().getStyle().setBorderColor(typeColor.getValue());
    field.getElement().getStyle().setBackgroundColor(backgroundColor.getValue());

    addByteField(field);
  }

  private void addByteField(final Label field) {
    if (byteSetContainer == null) {
      byteSetContainer = new FlowPanel();
      byteSetContainer.setStyleName(style.byteSet());
      fieldPanel.add(byteSetContainer);
    }

    byteSetContainer.add(field);

    if (byteSetContainer.getWidgetCount() == 8) {
      byteSetContainer = null;
    }
  }

  protected abstract Widget createContextPopupContent(final T valueContainer);

  protected abstract Color getColorForValue(final T type);

  public void clear() {
    popupPanel.hide();
    selectedField = null;
    fieldPanel.clear();
    byteSetContainer = null;
  }
}
