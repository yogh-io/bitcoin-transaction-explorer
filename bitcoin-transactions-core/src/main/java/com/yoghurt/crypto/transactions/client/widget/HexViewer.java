package com.yoghurt.crypto.transactions.client.widget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.util.misc.Color;

public abstract class HexViewer<T> extends ContextFieldSet<Entry<T, byte[]>> {
  interface HexViewerUiBinder extends UiBinder<Widget, HexViewer<?>> {}

  private static final HexViewerUiBinder UI_BINDER = GWT.create(HexViewerUiBinder.class);

  public interface CustomStyle extends CssResource {
    String byteSet();
  }

  @UiField CustomStyle style;

  private FlowPanel byteSetContainer;

  private ArrayList<ContextField<Entry<T, byte[]>>> activeFields;

  public HexViewer() {
    this(null);
  }

  public HexViewer(final FieldContextFactory<Entry<T, byte[]>> contextFactory) {
    super(contextFactory);

    UI_BINDER.createAndBindUi(this);
  }

  public void setContainerMap(final Map<T, byte[]> container) {
    setContainer(container.entrySet());
  }

  /**
   * Set the container for this hex viewer.
   */
  public void setContainer(final Collection<Entry<T, byte[]>> container) {
    boolean quickAdd = false;
    int counter = 0;
    for (final Entry<T, byte[]> entry : container) {
      final byte[] bytesForValue = getBytesForValue(entry);

      final Color typeColor = getFieldColor(entry);

      // Change the value for each field
      for (final byte bite : bytesForValue) {
        final String hex = getHexFromByte(bite);

        if(quickAdd || counter >= fields.size()) {
          addField(entry, typeColor, hex);
          quickAdd = true;
        } else {
          final ContextField<Entry<T, byte[]>> contextField = fields.get(counter);
          contextField.setValue(entry);
          contextField.setColor(typeColor);
          contextField.setContent(hex);
        }

        counter++;
      }
    }

    // Reverse remove the remainder
    for(int i = fields.size() - 1;i >= counter; i--) {
      removeField(i);
    }
  }

  public void addFields(final Entry<T, byte[]> value) {
    final Color typeColor = getFieldColor(value);

    for (final byte bite : getBytesForValue(value)) {
      final String hex = getHexFromByte(bite);

      addField(value, typeColor, hex);
    }
  }

  protected String getHexFromByte(final byte bite) {
    return new String(Hex.encode(new byte[] { bite })).toUpperCase();
  }

  @Override
  protected void displayContextForValue(final Entry<T, byte[]> value) {
    clearActiveFields();

    //    activeFields = findValueFields(value);

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

  protected byte[] getBytesForValue(final Entry<T, byte[]> entry) {
    return entry.getValue();
  }

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
  public void removeField(final int index) {
    final ContextField<Entry<T, byte[]>> contextField = fields.get(index);

    final FlowPanel byteSetContainer = (FlowPanel) contextField.getParent();

    contextField.removeFromParent();

    if(byteSetContainer.getWidgetCount() == 0) {
      byteSetContainer.removeFromParent();
    } else {
      this.byteSetContainer = byteSetContainer;
    }

    fields.remove(index);
  }

  @Override
  public void clear() {
    super.clear();
    byteSetContainer = null;
    clearActiveFields();
  }
}
