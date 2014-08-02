package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.widget.Arrow.Orientation;

public class AttachedContextPanel extends PopupPanel {
  private final Arrow arr;
  private Widget widget;

  /**
   * Default constructor.
   */
  public AttachedContextPanel() {
    this(false);
  }

  /**
   * Constructor taking auto close argument.
   *
   * @param b auto-close boolean
   */
  public AttachedContextPanel(final boolean b) {
    super(b);

    arr = new Arrow(Orientation.BOTTOM, 1);
    arr.setHeight(10);
    arr.setWidth(10);
    arr.setColor("#FFFFFF", "#BBBBBB");

    getContainerElement().appendChild(arr.getElement());
  }

  /**
   * Attaches this popup to the given widget and repositions the arrow to be
   * placed in the middle of the given widget.
   *
   * @param w Widget to attach to
   */
  public void attachToWidget(final Widget w) {
    this.widget = w;
    setPopupPosition(w.getAbsoluteLeft() + w.getOffsetWidth() / 2 - getWidget().getOffsetWidth() / 2, w.getAbsoluteTop() - getWidget().getOffsetHeight() - 10);
    updateArrowPosition();
  }

  @Override
  public void onLoad() {
    updateArrowPosition();
  }

  /**
   * Set the border and inner color on the arrow. Because of the complex CSS
   * required to create the arrow it's more convenient to set it programmatically
   * than it is to set it in CSS.
   *
   * @param borderColor Color of the border of the arrow
   * @param backgroundColor Color of the background of the arrow
   */
  public void setArrowColor(final String borderColor, final String backgroundColor) {
    arr.setBorderColor(backgroundColor);
    arr.setBackgroundColor(backgroundColor);
  }

  private void updateArrowPosition() {
    if (widget != null && widget.isAttached()) {
      arr.setPosition(widget.getAbsoluteLeft() - getAbsoluteLeft() + widget.getOffsetWidth() / 2 - 10);
    }
  }
}