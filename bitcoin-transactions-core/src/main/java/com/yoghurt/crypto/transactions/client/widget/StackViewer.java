package com.yoghurt.crypto.transactions.client.widget;

import java.util.Collection;

import com.google.gwt.dom.client.Style.Unit;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.domain.StackObject;
import com.yoghurt.crypto.transactions.client.resources.R;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.client.util.misc.EllipsisUtil;

public class StackViewer extends ContextFieldSet<StackObject> {

  public StackViewer() {
    super(new SimpleStackContextFactory());

    addStyleName(R.css().panel());
  }

  private static final int HASH_ELLIPSIS = 16;

  public void setStack(final Collection<StackObject> stack) {
    clear();
    for (final StackObject entry : stack) {
      addField(entry);
    }
  }

  @Override
  protected Color getFieldColor(final StackObject value) {
    return value.getBytes().length == 1 ? R.color().stackSingle() : R.color().stackData();
  }

  @Override
  protected String getFieldText(final StackObject value) {
    return EllipsisUtil.applyInner(Str.toString(Hex.encode(value.getBytes())).toUpperCase(), HASH_ELLIPSIS);
  }

  @Override
  protected ContextField<StackObject> addField(final ContextField<StackObject> field) {
    if(fields.size() > 1) {
      field.getElement().getStyle().setMarginTop(10, Unit.PX);
    }

    return super.addField(field);
  }
}
