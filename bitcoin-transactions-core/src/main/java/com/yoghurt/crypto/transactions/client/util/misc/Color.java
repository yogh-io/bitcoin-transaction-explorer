package com.yoghurt.crypto.transactions.client.util.misc;

public class Color {
  private int r;
  private int g;
  private int b;
  private double a;

  public Color(final int r, final int g, final int b) {
    this(r, g, b, 1);
  }

  public Color(final int r, final int g, final int b, final double a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  }

  public int getR() {
    return r;
  }

  public void setR(final int r) {
    this.r = r;
  }

  public int getG() {
    return g;
  }

  public void setG(final int g) {
    this.g = g;
  }

  public int getB() {
    return b;
  }

  public void setB(final int b) {
    this.b = b;
  }

  public double getA() {
    return a;
  }

  public void setA(final double a) {
    this.a = a;
  }

  public Color copy() {
    return new Color(r, g, b, a);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(a);
    result = prime * result + (int) (temp ^ temp >>> 32);
    result = prime * result + b;
    result = prime * result + g;
    result = prime * result + r;
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }

    return equals((Color)obj);
  }

  public boolean equals(final Color other) {
    return r == other.r && g == other.g && b == other.b && a == other.a;
  }

  public String getValue() {
    return "rgba(" + r + "," + g + "," + b + "," + a + ")";
  }

  @Override
  public String toString() {
    return "Color [r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + "]";
  }
}
