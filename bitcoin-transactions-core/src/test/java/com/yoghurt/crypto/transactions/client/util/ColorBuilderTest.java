package com.yoghurt.crypto.transactions.client.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class ColorBuilderTest {
  @Test
  public void testColorInterpret() {
    assertEquals(new Color(255, 255, 255), ColorBuilder.interpret("#ffffff"));
    assertEquals(new Color(255, 0, 0), ColorBuilder.interpret("#ff0000"));
    assertEquals(new Color(0, 255, 0), ColorBuilder.interpret("#00ff00"));
    assertEquals(new Color(0, 0, 255), ColorBuilder.interpret("#0000ff"));
    assertEquals(new Color(0, 0, 0), ColorBuilder.interpret("#000000"));
    assertEquals(new Color(255, 0, 0), ColorBuilder.interpret("red"));
    assertEquals(new Color(0, 0, 255), ColorBuilder.interpret("blue"));
    assertNotEquals(new Color(255, 0, 0), ColorBuilder.interpret("blue"));
  }
}
