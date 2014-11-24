package com.yoghurt.crypto.transactions.client.widget;

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

  private FlowPanel byteSetContainer;

  public HexViewer(final FieldContextFactory<T> contextFactory) {
    super(contextFactory);

    UI_BINDER.createAndBindUi(this);
  }

  public void addFields(final T value) {
    final Color typeColor = getFieldColor(value);

    for (final byte bite : getBytesForValue(value)) {
      final String hex = new String(Hex.encode(new byte[] {  bite })).toUpperCase();

      addField(value, typeColor, hex);
    }
  }

  protected abstract byte[] getBytesForValue(final T value);

  @Override
  protected void addField(final ContextField<T> field) {
    if (byteSetContainer == null) {
      byteSetContainer = new FlowPanel();
      byteSetContainer.setStyleName(style.byteSet());
      add(byteSetContainer);
    }

    byteSetContainer.add(field);

    if (byteSetContainer.getWidgetCount() == 8) {
      byteSetContainer = null;
    }
  }

  @Override
  public void clear() {
    super.clear();
    byteSetContainer = null;
  }
}
