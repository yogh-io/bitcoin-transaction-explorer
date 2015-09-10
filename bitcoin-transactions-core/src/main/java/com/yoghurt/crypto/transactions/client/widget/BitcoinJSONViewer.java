package com.yoghurt.crypto.transactions.client.widget;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;

public class BitcoinJSONViewer extends FlowPanel {
  private static final int MAX_HEADER = 4;

  private final BitcoinPlaceRouter router;

  public BitcoinJSONViewer(final BitcoinPlaceRouter router) {
    this.router = router;
  }

  public void setValue(final String json) {
    final JSONValue tree = JSONParser.parseStrict(json);

    clear();

    display(2, this, tree);
  }

  private void display(final int n, final FlowPanel container, final JSONValue tree) {
    JSONObject jsonObject;
    JSONString jsonString;
    JSONNumber jsonNumber;
    JSONArray jsonArray;
    final JSONBoolean jsonBoolean;

    // Just wanna point out the internal workings of JSONValue are weird as fuck
    if ((jsonObject = tree.isObject()) != null) {
      drawObject(container, n, jsonObject);
    } else if ((jsonString = tree.isString()) != null) {
      drawString(container, jsonString.stringValue());
    } else if ((jsonNumber = tree.isNumber()) != null) {
      drawNumber(container, jsonNumber.doubleValue());
    } else if (tree.isNull() != null) {
      drawNull(container);
    } else if ((jsonArray = tree.isArray()) != null) {
      drawArray(n, container, jsonArray);
    } else if ((jsonBoolean = tree.isBoolean()) != null) {
      drawBoolean(container, jsonBoolean.booleanValue());
    }
  }

  private Widget createHeader(final int n, final String key) {
    return new HeadingWidget(Math.min(MAX_HEADER, n), key);
  }

  private void drawObject(final FlowPanel container, final int n, final JSONObject jsonObject) {
    for (final String key : jsonObject.keySet()) {
      container.add(createHeader(n, key));

      final FlowPanel subPanel = new FlowPanel();

      container.add(subPanel);
      subPanel.getElement().getStyle().setProperty("border", "2px solid gray");
      subPanel.getElement().getStyle().setProperty("marginLeft", "4px");
      subPanel.getElement().getStyle().setProperty("padding", "12px");

      display(n + 1, subPanel, jsonObject.get(key));
    }
  }

  private void drawArray(final int n, final FlowPanel container, final JSONArray jsonArray) {
    for (int i = 0; i < jsonArray.size(); i++) {
      display(n + 1, container, jsonArray.get(i));
    }
  }

  private void drawNull(final FlowPanel container) {
    container.add(new Label("Null"));
  }

  private void drawBoolean(final FlowPanel container, final boolean bool) {
    container.add(new Label(String.valueOf(bool)));
  }

  private void drawNumber(final FlowPanel container, final double doubleValue) {
    container.add(new Label(String.valueOf(doubleValue)));
  }

  private void drawString(final HasWidgets container, final String stringValue) {
    container.add(new HTML(nl2br(SafeHtmlUtils.fromString(stringValue))));
  }

  private static String nl2br(final SafeHtml inp) {
    return inp.asString().replace("\n", "<br />");
  }
}
