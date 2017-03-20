package com.yoghurt.crypto.transactions.client.widget;

import java.util.Map.Entry;

import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.util.NumberParseUtil;
import com.yoghurt.crypto.transactions.client.util.block.BlockUtil;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.client.util.misc.ColorBuilder;

public class TargettedHashHexViewer extends HashHexViewer {
  private final Color[] colors = new Color[] {
      ColorBuilder.interpret("green", 0.2),
      ColorBuilder.interpret("3FFF00", 0.5),
      ColorBuilder.interpret("7FFF00", 0.6),
      ColorBuilder.interpret("BFFF00", 0.7),
      ColorBuilder.interpret("FFFF00", 0.75),
      ColorBuilder.interpret("FFBF00", 0.75),
      ColorBuilder.interpret("FF7F00", 0.75),
      ColorBuilder.interpret("FF3F00", 0.75),
      ColorBuilder.interpret("FF0000", 0.75)
  };

  private byte[] target;

  public TargettedHashHexViewer() {
    super();
  }

  public TargettedHashHexViewer(final Color color) {
    super(color);
  }

  public TargettedHashHexViewer(final Color color, final FieldContextFactory<Entry<byte[], byte[]>> factory) {
    super(color, factory);
  }

  @Override
  protected ContextField<Entry<byte[], byte[]>> createContextField(final Entry<byte[], byte[]> value, final Color color, final String text) {
    final byte counterPart = (byte) (target[fields.size()] & 0xFF);
    final int counterPartOnes = NumberParseUtil.countOnes(counterPart);

    return new ContextField<Entry<byte[], byte[]>>(value, color, text) {
      @Override
      public void setContent(final String text, final boolean animate) {
        super.setContent(text, animate);

        final byte[] decode = Hex.decode(text.getBytes());

        final byte bite = (byte) (decode[0] & 0xFF);

        final int numberOfOnes = (bite & 0xFF) < (counterPart & 0xFF) ? 0 : NumberParseUtil.countOnes(bite & 0xFF);

        getElement().getStyle().setBackgroundColor(colors[Math.max(0, numberOfOnes - counterPartOnes)].getValue());
      }
    };
  }

  public void setDifficulty(final byte[] bits) {
    target = BlockUtil.getPoolDiffTarget(bits);
    clear();
  }
}
