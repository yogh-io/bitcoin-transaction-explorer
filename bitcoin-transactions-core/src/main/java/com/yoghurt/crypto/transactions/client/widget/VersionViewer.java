package com.yoghurt.crypto.transactions.client.widget;

import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.resources.R;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;
import com.yoghurt.crypto.transactions.shared.util.NumberEncodeUtil;

public class VersionViewer extends ValueViewer {
  public VersionViewer() {
    super(R.color().blockVersion());
  }

  public void setValue(final Long version) {
    final byte[] hashCopy = NumberEncodeUtil.encodeUint32(version);

    ArrayUtil.reverse(hashCopy);

    super.setValue("0x" + Str.toString(Hex.encode(hashCopy)).toUpperCase());
  }
}
