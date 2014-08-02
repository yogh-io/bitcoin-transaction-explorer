package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Class to create a arrow like element that can be attached to other panels to
 * show an arrow.
 */
public class Arrow extends UIObject {

  /**
   * Enum for setting the orientation of the arrow.
   */
  public static enum Orientation {
    /**
     * Arrow points downwards.
     */
    BOTTOM("Bottom", "Top", "Left", "Right"),
    /**
     * Arrow points to the left.
     */
    LEFT("Left", "Right", "Top", "Bottom"),
    /**
     * Arrow points to the right.
     */
    RIGHT("Right", "Left", "Top", "Bottom"),
    /**
     * Arrow points upwards.
     */
    TOP("Top", "Bottom", "Left", "Right");

    private final String pos;
    private final String altPos;
    private final String inStylePos;
    private final String inStyleOpPos;
    private final String inStyleAltOpPos;
    private final String inStyleAltPos;

    Orientation(final String inStylePos, final String inStyleOpPos, final String inStyleAltPos, final String inStyleAltOpPos) {
      this.pos = inStylePos.toLowerCase();
      this.altPos = inStyleAltPos.toLowerCase();
      this.inStylePos = inStylePos;
      this.inStyleOpPos = inStyleOpPos;
      this.inStyleAltPos = inStyleAltPos;
      this.inStyleAltOpPos = inStyleAltOpPos;
    }
  }

  private static final String TRANSPARENT = " transparent ";
  private static final String BORDER = "border";
  private static final String WIDTH = "Width";

  private final DivElement d = Document.get().createDivElement();
  private final DivElement d1 = Document.get().createDivElement();
  private final DivElement d2 = Document.get().createDivElement();

  private final Orientation orientation;
  private final boolean topBottom;
  private final int borderWidth;

  private int height;

  public Arrow(final Orientation orientation, final int borderWidth) {
    setElement(d);
    this.orientation = orientation;
    this.borderWidth = borderWidth;
    d.appendChild(d1);
    d.appendChild(d2);
    d.getStyle().setPosition(Position.ABSOLUTE);
    d1.getStyle().setPosition(Position.ABSOLUTE);
    d1.getStyle().setHeight(0, Unit.PX);
    d1.getStyle().setWidth(0, Unit.PX);
    d1.getStyle().setPropertyPx(orientation.pos, 0);
    d1.getStyle().setPropertyPx(orientation.altPos, 0);
    d1.getStyle().setBorderStyle(BorderStyle.SOLID);
    d2.getStyle().setPosition(Position.ABSOLUTE);
    d2.getStyle().setHeight(0, Unit.PX);
    d2.getStyle().setWidth(0, Unit.PX);
    d2.getStyle().setPropertyPx(orientation.pos, borderWidth);
    d2.getStyle().setPropertyPx(orientation.altPos, borderWidth);
    d2.getStyle().setBorderStyle(BorderStyle.SOLID);
    topBottom = orientation == Orientation.TOP || orientation == Orientation.BOTTOM;
  }

  public int getHeight() {
    return height;
  }

  public void setBackgroundColor(final String backgroundColor) {
    d2.getStyle().setBorderColor(topBottom
        ? backgroundColor + TRANSPARENT + backgroundColor + TRANSPARENT
            : TRANSPARENT + backgroundColor + TRANSPARENT + backgroundColor);
  }

  public void setBorderColor(final String borderColor) {
    d1.getStyle().setBorderColor(topBottom
        ? borderColor + TRANSPARENT + borderColor + TRANSPARENT
            : TRANSPARENT + borderColor + TRANSPARENT + borderColor);
  }

  public void setColor(final String backgroundColor, final String borderColor) {
    setBackgroundColor(backgroundColor);
    setBorderColor(borderColor);
  }

  public void setHeight(final int height) {
    this.height = height;
    d.getStyle().setPropertyPx(orientation.pos, -height);
    d1.getStyle().setPropertyPx(BORDER + orientation.inStylePos + WIDTH, 0);
    d1.getStyle().setPropertyPx(BORDER + orientation.inStyleOpPos + WIDTH, height);
    d2.getStyle().setPropertyPx(BORDER + orientation.inStylePos + WIDTH, 0);
    d2.getStyle().setPropertyPx(BORDER + orientation.inStyleOpPos + WIDTH, height - borderWidth);
  }

  public void setPosition(final int position) {
    d.getStyle().setPropertyPx(topBottom ? "left" : "top", position);
  }

  public void setSize(final int height, final int width) {
    setHeight(height);
    setWidth(width);
  }

  public void setSize(final int size) {
    setSize(size, size);
  }

  public void setWidth(final int width) {
    d1.getStyle().setPropertyPx(BORDER + orientation.inStyleAltPos + WIDTH, width);
    d1.getStyle().setPropertyPx(BORDER + orientation.inStyleAltOpPos + WIDTH, width);
    d2.getStyle().setPropertyPx(BORDER + orientation.inStyleAltPos + WIDTH, width - borderWidth);
    d2.getStyle().setPropertyPx(BORDER + orientation.inStyleAltOpPos + WIDTH, width - borderWidth);
  }
}