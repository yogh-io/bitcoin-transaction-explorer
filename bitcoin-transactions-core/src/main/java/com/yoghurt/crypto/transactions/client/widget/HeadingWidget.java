package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class HeadingWidget extends Widget implements HasText, HasHTML {
  @UiConstructor
  public HeadingWidget(final int n) {
    setElement(Document.get().createHElement(n));
  }

  @Override
  public String getText() {
    return getElement().getInnerText();
  }

  @Override
  public void setText(final String text) {
    getElement().setInnerText(text);
  }

  @Override
  public String getHTML() {
    return getElement().getInnerHTML();
  }

  @Override
  public void setHTML(final String html) {
    getElement().setInnerHTML(html);
  }
}