package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;
import com.yoghurt.crypto.transactions.client.widget.HeadingWidget;

@Singleton
public class RPCResponseViewImpl extends Composite implements RPCResponseView {
  interface ScriptViewImplUiBinder extends UiBinder<Widget, RPCResponseViewImpl> {
  }

  private static final ScriptViewImplUiBinder UI_BINDER = GWT.create(ScriptViewImplUiBinder.class);

  private static final int MAX_HEADER = 4;

  @UiField FlowPanel container;

  private final BitcoinPlaceRouter router;

  @Inject
  public RPCResponseViewImpl(final BitcoinPlaceRouter router) {
    this.router = router;
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setResponse(final String jsonString) {
    // Just wanna point out the internal workings of this object are weird as fuck
    final JSONValue tree = JSONParser.parseStrict(jsonString);

    container.clear();

    display(2, container, tree);
  }

  private void display(final int n, final FlowPanel container, final JSONValue tree) {
    JSONObject jsonObject;
    JSONString jsonString;
    JSONNumber jsonNumber;
    JSONArray jsonArray;
    final JSONBoolean jsonBoolean;

    if ((jsonObject = tree.isObject()) != null) {
      for (final String key : jsonObject.keySet()) {
        container.add(new HeadingWidget(Math.min(MAX_HEADER, n), key));
        final FlowPanel subPanel = new FlowPanel();

        container.add(subPanel);
        subPanel.getElement().getStyle().setProperty("border", "2px solid gray");
        subPanel.getElement().getStyle().setProperty("marginLeft", "4px");
        subPanel.getElement().getStyle().setProperty("padding", "12px");

        display(n + 1, subPanel, jsonObject.get(key));
      }
    } else if((jsonString = tree.isString()) != null) {
      container.add(new HTML(nl2br(SafeHtmlUtils.fromString(jsonString.stringValue()))));
    } else if((jsonNumber = tree.isNumber()) != null) {
      container.add(new Label(String.valueOf(jsonNumber.doubleValue())));
    } else if(tree.isNull() != null) {
      container.add(new Label("Null"));
    } else if((jsonArray = tree.isArray()) != null) {
      for(int i = 0; i < jsonArray.size(); i++) {
        display(n + 1, container, jsonArray.get(i));
      }
    } else if((jsonBoolean = tree.isBoolean()) != null) {
      container.add(new Label(String.valueOf(jsonBoolean.booleanValue())));
    }
  }

  private static String nl2br(final SafeHtml inp) {
    return inp.asString().replace("\n", "<br />");
  }
}
