package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Widget;

public class QRCodeWidget extends Widget implements TakesValue<String> {
  private boolean initted = false;

  private final String id;

  private String value;

  public QRCodeWidget() {
    id = DOM.createUniqueId();

    final Element div = Document.get().createDivElement();
    div.setId(id);

    setElement(div);
  }

  private native void initQRCodeImpl(String id, String value) /*-{
    var qr = new $wnd.QRCode($doc.getElementById(id), value);
  }-*/;

  private native void setQRCodeValue(String id, String value) /*-{
    var qr = new $wnd.QRCode($doc.getElementById(id), value);
  }-*/;

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public void setValue(final String value) {
    this.value = value;

    if(!initted) {
      initQRCodeImpl(id, value);
      initted = true;
    } else {
      setQRCodeValue(id, value);
    }
  }
}
