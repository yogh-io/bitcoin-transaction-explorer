package com.yoghurt.crypto.transactions.client.widget;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.Arrays;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.util.misc.Color;

public abstract class HexViewer<T> extends ContextFieldSet<Entry<T, byte[]>> {
  interface HexViewerUiBinder extends UiBinder<Widget, HexViewer<?>> {}

  private static final HexViewerUiBinder UI_BINDER = GWT.create(HexViewerUiBinder.class);

  public interface CustomStyle extends CssResource {
    String byteSet();
  }

  @UiField CustomStyle style;

  // Kind of a sketchy construct... it's basically a HashMap that allows duplicate values. Scales like a turd.
  protected final ArrayList<Entry<Entry<T, byte[]>, ArrayList<ContextField<Entry<T, byte[]>>>>> fieldMap = new ArrayList<Entry<Entry<T, byte[]>, ArrayList<ContextField<Entry<T, byte[]>>>>>();

  private FlowPanel byteSetContainer;

  private ArrayList<ContextField<Entry<T, byte[]>>> activeFields;

  public HexViewer(final FieldContextFactory<Entry<T, byte[]>> contextFactory) {
    super(contextFactory);

    UI_BINDER.createAndBindUi(this);
  }

  /**
   * Set the container for this hex viewer.
   * 
   * WARNING: Unsafe for containers that are not the exact same length (!)
   * 
   * FIXME Fix the above.
   */
  public void setContainer(final Map<T, byte[]> container) {
    // If the container's byte size is not the exact same as the field map
    if (fieldMap.isEmpty()) {
      resetContainer(container);
    } else {
      updateContainer(container);
    }
  }

  public void resetContainer(final Map<T, byte[]> container) {
    clear();

    for (final Entry<T, byte[]> part : container.entrySet()) {
      addFields(part);
    }
  }

  private void updateContainer(final Map<T, byte[]> container) {
    for (final Entry<T, byte[]> entry : container.entrySet()) {
      final ArrayList<ContextField<Entry<T, byte[]>>> field = findValueFields(entry);

      if(field == null) {
        GWT.log("Field could not be found: " + entry.getKey());
        continue;
      }

      // Field shouldn't be empty, but check for it anyway
      if (field.isEmpty()) {
        continue;
      }

      final byte[] bytesForValue = getBytesForValue(entry);

      // If the value is equal, keep going
      if (Arrays.areEqual(bytesForValue, field.get(0).getValue().getValue())) {
        continue;
      }

      // Fields should be of equal size, but check for it anyway
      if (bytesForValue.length != field.size()) {
        GWT.log("Size inconsistency");
        // TODO Log and bug out.
        continue;
      }

      // Change the value for each field
      int i = 0;
      for (final byte bite : bytesForValue) {
        final ContextField<Entry<T, byte[]>> contextField = field.get(i++);
        contextField.setContent(getHexFromByte(bite));

        // Disgusting...
        contextField.getValue().setValue(entry.getValue());
      }
    }
  }

  private boolean sameSize(final Map<T, byte[]> container) {
    int counter = 0;
    for(final byte[] bytes : container.values()) {
      counter += bytes.length;
    }

    GWT.log(counter + " > " + fields.size());

    return fields.size() == counter;
  }

  public void addFields(final Entry<T, byte[]> value) {
    final Color typeColor = getFieldColor(value);

    final ArrayList<ContextField<Entry<T, byte[]>>> fields = new ArrayList<ContextField<Entry<T, byte[]>>>();

    for (final byte bite : getBytesForValue(value)) {
      final String hex = getHexFromByte(bite);

      fields.add(addField(value, typeColor, hex));
    }

    fieldMap.add(new AbstractMap.SimpleEntry<Entry<T, byte[]>, ArrayList<ContextField<Entry<T, byte[]>>>>(value, fields));
  }

  protected String getHexFromByte(final byte bite) {
    return new String(Hex.encode(new byte[] { bite })).toUpperCase();
  }

  @Override
  protected void displayContextForValue(final Entry<T, byte[]> value) {
    clearActiveFields();

    activeFields = findValueFields(value);

    if (activeFields == null) {
      return;
    }

    for (final ContextField<Entry<T, byte[]>> w : activeFields) {
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
      for (final ContextField<Entry<T, byte[]>> field : activeFields) {
        field.setActive(false);
      }
    }
  }

  protected ArrayList<ContextField<Entry<T, byte[]>>> findValueFields(final Entry<T, byte[]> value) {
    for (final Entry<Entry<T, byte[]>, ArrayList<ContextField<Entry<T, byte[]>>>> entry : fieldMap) {
      if (entry.getKey() == value) {
        return entry.getValue();
      }
    }

    return null;
  }

  protected abstract byte[] getBytesForValue(final Entry<T, byte[]> value);

  @Override
  protected ContextField<Entry<T, byte[]>> addField(final ContextField<Entry<T, byte[]>> field) {
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
