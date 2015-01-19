package com.yoghurt.crypto.transactions.client.util.misc;

import java.util.HashMap;

public final class ColorBuilder {
  // List of all CSS1 colors
  private static final HashMap<String, Color> COLORS = new HashMap<String, Color>();

  static {
    COLORS.put("aqua", interpretHex("00ffff"));
    COLORS.put("aliceblue", interpretHex("f0f8ff"));
    COLORS.put("antiquewhite", interpretHex("faebd7"));
    COLORS.put("black", interpretHex("000000"));
    COLORS.put("blue", interpretHex("0000ff"));
    COLORS.put("cyan", interpretHex("00ffff"));
    COLORS.put("darkblue", interpretHex("00008b"));
    COLORS.put("darkcyan", interpretHex("008b8b"));
    COLORS.put("darkgreen", interpretHex("006400"));
    COLORS.put("darkturquoise", interpretHex("00ced1"));
    COLORS.put("deepskyblue", interpretHex("00bfff"));
    COLORS.put("green", interpretHex("008000"));
    COLORS.put("lime", interpretHex("00ff00"));
    COLORS.put("mediumblue", interpretHex("0000cd"));
    COLORS.put("mediumspringgreen", interpretHex("00fa9a"));
    COLORS.put("navy", interpretHex("000080"));
    COLORS.put("springgreen", interpretHex("00ff7f"));
    COLORS.put("teal", interpretHex("008080"));
    COLORS.put("midnightblue", interpretHex("191970"));
    COLORS.put("dodgerblue", interpretHex("1e90ff"));
    COLORS.put("lightseagreen", interpretHex("20b2aa"));
    COLORS.put("forestgreen", interpretHex("228b22"));
    COLORS.put("seagreen", interpretHex("2e8b57"));
    COLORS.put("darkslategray", interpretHex("2f4f4f"));
    COLORS.put("darkslategrey", interpretHex("2f4f4f"));
    COLORS.put("limegreen", interpretHex("32cd32"));
    COLORS.put("mediumseagreen", interpretHex("3cb371"));
    COLORS.put("turquoise", interpretHex("40e0d0"));
    COLORS.put("royalblue", interpretHex("4169e1"));
    COLORS.put("steelblue", interpretHex("4682b4"));
    COLORS.put("darkslateblue", interpretHex("483d8b"));
    COLORS.put("mediumturquoise", interpretHex("48d1cc"));
    COLORS.put("indigo", interpretHex("4b0082"));
    COLORS.put("darkolivegreen", interpretHex("556b2f"));
    COLORS.put("cadetblue", interpretHex("5f9ea0"));
    COLORS.put("cornflowerblue", interpretHex("6495ed"));
    COLORS.put("mediumaquamarine", interpretHex("66cdaa"));
    COLORS.put("dimgray", interpretHex("696969"));
    COLORS.put("dimgrey", interpretHex("696969"));
    COLORS.put("slateblue", interpretHex("6a5acd"));
    COLORS.put("olivedrab", interpretHex("6b8e23"));
    COLORS.put("slategray", interpretHex("708090"));
    COLORS.put("slategrey", interpretHex("708090"));
    COLORS.put("lightslategray", interpretHex("778899"));
    COLORS.put("lightslategrey", interpretHex("778899"));
    COLORS.put("mediumslateblue", interpretHex("7b68ee"));
    COLORS.put("lawngreen", interpretHex("7cfc00"));
    COLORS.put("aquamarine", interpretHex("7fffd4"));
    COLORS.put("chartreuse", interpretHex("7fff00"));
    COLORS.put("gray", interpretHex("808080"));
    COLORS.put("grey", interpretHex("808080"));
    COLORS.put("maroon", interpretHex("800000"));
    COLORS.put("olive", interpretHex("808000"));
    COLORS.put("purple", interpretHex("800080"));
    COLORS.put("lightskyblue", interpretHex("87cefa"));
    COLORS.put("skyblue", interpretHex("87ceeb"));
    COLORS.put("blueviolet", interpretHex("8a2be2"));
    COLORS.put("darkmagenta", interpretHex("8b008b"));
    COLORS.put("darkred", interpretHex("8b0000"));
    COLORS.put("saddlebrown", interpretHex("8b4513"));
    COLORS.put("darkseagreen", interpretHex("8fbc8f"));
    COLORS.put("lightgreen", interpretHex("90ee90"));
    COLORS.put("mediumpurple", interpretHex("9370db"));
    COLORS.put("darkviolet", interpretHex("9400d3"));
    COLORS.put("palegreen", interpretHex("98fb98"));
    COLORS.put("darkorchid", interpretHex("9932cc"));
    COLORS.put("yellowgreen", interpretHex("9acd32"));
    COLORS.put("sienna", interpretHex("a0522d"));
    COLORS.put("brown", interpretHex("a52a2a"));
    COLORS.put("darkgray", interpretHex("a9a9a9"));
    COLORS.put("darkgrey", interpretHex("a9a9a9"));
    COLORS.put("greenyellow", interpretHex("adff2f"));
    COLORS.put("lightblue", interpretHex("add8e6"));
    COLORS.put("paleturquoise", interpretHex("afeeee"));
    COLORS.put("lightsteelblue", interpretHex("b0c4de"));
    COLORS.put("powderblue", interpretHex("b0e0e6"));
    COLORS.put("firebrick", interpretHex("b22222"));
    COLORS.put("darkgoldenrod", interpretHex("b8860b"));
    COLORS.put("mediumorchid", interpretHex("ba55d3"));
    COLORS.put("rosybrown", interpretHex("bc8f8f"));
    COLORS.put("darkkhaki", interpretHex("bdb76b"));
    COLORS.put("silver", interpretHex("c0c0c0"));
    COLORS.put("mediumvioletred", interpretHex("c71585"));
    COLORS.put("indianred", interpretHex("cd5c5c"));
    COLORS.put("peru", interpretHex("cd853f"));
    COLORS.put("chocolate", interpretHex("d2691e"));
    COLORS.put("tan", interpretHex("d2b48c"));
    COLORS.put("lightgray", interpretHex("d3d3d3"));
    COLORS.put("lightgrey", interpretHex("d3d3d3"));
    COLORS.put("thistle", interpretHex("d8bfd8"));
    COLORS.put("goldenrod", interpretHex("daa520"));
    COLORS.put("orchid", interpretHex("da70d6"));
    COLORS.put("palevioletred", interpretHex("db7093"));
    COLORS.put("crimson", interpretHex("dc143c"));
    COLORS.put("gainsboro", interpretHex("dcdcdc"));
    COLORS.put("plum", interpretHex("dda0dd"));
    COLORS.put("burlywood", interpretHex("deb887"));
    COLORS.put("lightcyan", interpretHex("e0ffff"));
    COLORS.put("lavender", interpretHex("e6e6fa"));
    COLORS.put("darksalmon", interpretHex("e9967a"));
    COLORS.put("palegoldenrod", interpretHex("eee8aa"));
    COLORS.put("violet", interpretHex("ee82ee"));
    COLORS.put("azure", interpretHex("f0ffff"));
    COLORS.put("honeydew", interpretHex("f0fff0"));
    COLORS.put("khaki", interpretHex("f0e68c"));
    COLORS.put("lightcoral", interpretHex("f08080"));
    COLORS.put("sandybrown", interpretHex("f4a460"));
    COLORS.put("beige", interpretHex("f5f5dc"));
    COLORS.put("mintcream", interpretHex("f5fffa"));
    COLORS.put("wheat", interpretHex("f5deb3"));
    COLORS.put("whitesmoke", interpretHex("f5f5f5"));
    COLORS.put("ghostwhite", interpretHex("f8f8ff"));
    COLORS.put("lightgoldenrodyellow", interpretHex("fafad2"));
    COLORS.put("linen", interpretHex("faf0e6"));
    COLORS.put("salmon", interpretHex("fa8072"));
    COLORS.put("oldlace", interpretHex("fdf5e6"));
    COLORS.put("bisque", interpretHex("ffe4c4"));
    COLORS.put("blanchedalmond", interpretHex("ffebcd"));
    COLORS.put("coral", interpretHex("ff7f50"));
    COLORS.put("cornsilk", interpretHex("fff8dc"));
    COLORS.put("darkorange", interpretHex("ff8c00"));
    COLORS.put("deeppink", interpretHex("ff1493"));
    COLORS.put("floralwhite", interpretHex("fffaf0"));
    COLORS.put("fuchsia", interpretHex("ff00ff"));
    COLORS.put("gold", interpretHex("ffd700"));
    COLORS.put("hotpink", interpretHex("ff69b4"));
    COLORS.put("ivory", interpretHex("fffff0"));
    COLORS.put("lavenderblush", interpretHex("fff0f5"));
    COLORS.put("lemonchiffon", interpretHex("fffacd"));
    COLORS.put("lightpink", interpretHex("ffb6c1"));
    COLORS.put("lightsalmon", interpretHex("ffa07a"));
    COLORS.put("lightyellow", interpretHex("ffffe0"));
    COLORS.put("magenta", interpretHex("ff00ff"));
    COLORS.put("mistyrose", interpretHex("ffe4e1"));
    COLORS.put("moccasin", interpretHex("ffe4b5"));
    COLORS.put("navajowhite", interpretHex("ffdead"));
    COLORS.put("orange", interpretHex("ffa500"));
    COLORS.put("orangered", interpretHex("ff4500"));
    COLORS.put("papayawhip", interpretHex("ffefd5"));
    COLORS.put("peachpuff", interpretHex("ffdab9"));
    COLORS.put("pink", interpretHex("ffc0cb"));
    COLORS.put("red", interpretHex("ff0000"));
    COLORS.put("seashell", interpretHex("fff5ee"));
    COLORS.put("snow", interpretHex("fffafa"));
    COLORS.put("tomato", interpretHex("ff6347"));
    COLORS.put("white", interpretHex("ffffff"));
    COLORS.put("yellow", interpretHex("ffff00"));
    COLORS.put("rebeccapurple", interpretHex("663399"));
  }

  public static Color interpret(final String color) {
    final String fixedColor = color.toLowerCase().replace("-", "");

    if (COLORS.containsKey(fixedColor)) {
      return COLORS.get(fixedColor);
    }

    return interpretHex(color);
  }

  private static Color interpretHex(final String hex) {
    if(hex.startsWith("#")) {
      return interpretHex(hex.substring(1));
    }

    final int r = Integer.parseInt(hex.substring(0, 2).toUpperCase(), 16);
    final int g = Integer.parseInt(hex.substring(2, 4).toUpperCase(), 16);
    final int b = Integer.parseInt(hex.substring(4, 6).toUpperCase(), 16);

    return new Color(r, g, b);
  }
}
