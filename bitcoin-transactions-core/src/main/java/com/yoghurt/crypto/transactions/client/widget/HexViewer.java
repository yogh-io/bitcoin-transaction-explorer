package com.yoghurt.crypto.transactions.client.widget;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.util.misc.Color;

public abstract class HexViewer<T> extends ContextFieldSet<T> {
  interface HexViewerUiBinder extends UiBinder<Widget, HexViewer<?>> {}

  private static final HexViewerUiBinder UI_BINDER = GWT.create(HexViewerUiBinder.class);

  public interface CustomStyle extends CssResource {
    String byteSet();
  }

  @UiField CustomStyle style;

  // Kind of a sketchy construct... it's basically a HashMap that allows duplicate values. Scales like a turd.
  protected final ArrayList<Entry<T, ArrayList<ContextField<T>>>> fieldMap = new ArrayList<Entry<T, ArrayList<ContextField<T>>>>();

  private FlowPanel byteSetContainer;

  private ArrayList<ContextField<T>> activeFields;

  public HexViewer(final FieldContextFactory<T> contextFactory) {
    super(contextFactory);

    UI_BINDER.createAndBindUi(this);
  }

  public void addFields(final T value) {
    final Color typeColor = getFieldColor(value);

    final ArrayList<ContextField<T>> fields = new ArrayList<ContextField<T>>();

    for (final byte bite : getBytesForValue(value)) {
      final String hex = getHexFromByte(bite);

      fields.add(addField(value, typeColor, hex));
    }

    fieldMap.add(new AbstractMap.SimpleEntry<T, ArrayList<ContextField<T>>>(value, fields));
  }

  protected String getHexFromByte(final byte bite) {
    return new String(Hex.encode(new byte[] { bite })).toUpperCase();
  }

  @Override
  protected void displayContextForValue(final T value) {
    clearActiveFields();

    activeFields = findValueFields(value);

    if (activeFields == null) {
      return;
    }

    for (final ContextField<T> w : activeFields) {
      w.setActive(true);
    }
  }

  @Override
  protected void clearActivity() {
    super.clearActivity();

    clearActiveFields();
  }

  private void clearActiveFields() {
    if (activeFields != null) {
      for (final ContextField<T> field : activeFields) {
        field.setActive(false);
      }
    }
  }

  protected ArrayList<ContextField<T>> findValueFields(final T value) {
    for (final Entry<T, ArrayList<ContextField<T>>> entry : fieldMap) {
      if (entry.getKey() == value) {
        return entry.getValue();
      }
    }

    return null;
  }

  protected abstract byte[] getBytesForValue(final T value);

  @Override
  protected ContextField<T> addField(final ContextField<T> field) {
    if (byteSetContainer == null) {
      byteSetContainer = new FlowPanel();
      byteSetContainer.setStyleName(style.byteSet());
      add(byteSetContainer);
    }

    byteSetContainer.add(field);

    if (byteSetContainer.getWidgetCount() == 8) {
      byteSetContainer = null;
    }

    return field;
  }

  @Override
  public void clear() {
    super.clear();
    byteSetContainer = null;
    clearActiveFields();
  }
}
