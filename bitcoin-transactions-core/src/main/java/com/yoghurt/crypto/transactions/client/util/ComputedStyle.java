package com.yoghurt.crypto.transactions.client.util;

import com.google.gwt.dom.client.Element;

public class ComputedStyle {
  public static native String getStyleProperty(Element el, String prop) /*-{
		var computedStyle;
		if (document.defaultView && document.defaultView.getComputedStyle) {
			computedStyle = document.defaultView.getComputedStyle(el, null)[prop];

		} else if (el.currentStyle) {
			computedStyle = el.currentStyle[prop];

		} else {
			computedStyle = el.style[prop];
		}
		return computedStyle;
  }-*/;

  public static native String getStyle(Element el, String prop) /*-{
		var style = el.style[prop];
		return style;
  }-*/;

}