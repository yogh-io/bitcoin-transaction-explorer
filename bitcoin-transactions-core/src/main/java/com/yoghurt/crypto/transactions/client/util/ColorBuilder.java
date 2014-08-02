package com.yoghurt.crypto.transactions.client.util;

import java.util.HashMap;

public final class ColorBuilder {
  // List of all CSS1 colors
  private static final HashMap<String, String> COLORS = new HashMap<String, String>();

  static {
    COLORS.put("aqua", "00ffff");
    COLORS.put("aliceblue", "f0f8ff");
    COLORS.put("antiquewhite", "faebd7");
    COLORS.put("black", "000000");
    COLORS.put("blue", "0000ff");
    COLORS.put("cyan", "00ffff");
    COLORS.put("darkblue", "00008b");
    COLORS.put("darkcyan", "008b8b");
    COLORS.put("darkgreen", "006400");
    COLORS.put("darkturquoise", "00ced1");
    COLORS.put("deepskyblue", "00bfff");
    COLORS.put("green", "008000");
    COLORS.put("lime", "00ff00");
    COLORS.put("mediumblue", "0000cd");
    COLORS.put("mediumspringgreen", "00fa9a");
    COLORS.put("navy", "000080");
    COLORS.put("springgreen", "00ff7f");
    COLORS.put("teal", "008080");
    COLORS.put("midnightblue", "191970");
    COLORS.put("dodgerblue", "1e90ff");
    COLORS.put("lightseagreen", "20b2aa");
    COLORS.put("forestgreen", "228b22");
    COLORS.put("seagreen", "2e8b57");
    COLORS.put("darkslategray", "2f4f4f");
    COLORS.put("darkslategrey", "2f4f4f");
    COLORS.put("limegreen", "32cd32");
    COLORS.put("mediumseagreen", "3cb371");
    COLORS.put("turquoise", "40e0d0");
    COLORS.put("royalblue", "4169e1");
    COLORS.put("steelblue", "4682b4");
    COLORS.put("darkslateblue", "483d8b");
    COLORS.put("mediumturquoise", "48d1cc");
    COLORS.put("indigo", "4b0082");
    COLORS.put("darkolivegreen", "556b2f");
    COLORS.put("cadetblue", "5f9ea0");
    COLORS.put("cornflowerblue", "6495ed");
    COLORS.put("mediumaquamarine", "66cdaa");
    COLORS.put("dimgray", "696969");
    COLORS.put("dimgrey", "696969");
    COLORS.put("slateblue", "6a5acd");
    COLORS.put("olivedrab", "6b8e23");
    COLORS.put("slategray", "708090");
    COLORS.put("slategrey", "708090");
    COLORS.put("lightslategray", "778899");
    COLORS.put("lightslategrey", "778899");
    COLORS.put("mediumslateblue", "7b68ee");
    COLORS.put("lawngreen", "7cfc00");
    COLORS.put("aquamarine", "7fffd4");
    COLORS.put("chartreuse", "7fff00");
    COLORS.put("gray", "808080");
    COLORS.put("grey", "808080");
    COLORS.put("maroon", "800000");
    COLORS.put("olive", "808000");
    COLORS.put("purple", "800080");
    COLORS.put("lightskyblue", "87cefa");
    COLORS.put("skyblue", "87ceeb");
    COLORS.put("blueviolet", "8a2be2");
    COLORS.put("darkmagenta", "8b008b");
    COLORS.put("darkred", "8b0000");
    COLORS.put("saddlebrown", "8b4513");
    COLORS.put("darkseagreen", "8fbc8f");
    COLORS.put("lightgreen", "90ee90");
    COLORS.put("mediumpurple", "9370db");
    COLORS.put("darkviolet", "9400d3");
    COLORS.put("palegreen", "98fb98");
    COLORS.put("darkorchid", "9932cc");
    COLORS.put("yellowgreen", "9acd32");
    COLORS.put("sienna", "a0522d");
    COLORS.put("brown", "a52a2a");
    COLORS.put("darkgray", "a9a9a9");
    COLORS.put("darkgrey", "a9a9a9");
    COLORS.put("greenyellow", "adff2f");
    COLORS.put("lightblue", "add8e6");
    COLORS.put("paleturquoise", "afeeee");
    COLORS.put("lightsteelblue", "b0c4de");
    COLORS.put("powderblue", "b0e0e6");
    COLORS.put("firebrick", "b22222");
    COLORS.put("darkgoldenrod", "b8860b");
    COLORS.put("mediumorchid", "ba55d3");
    COLORS.put("rosybrown", "bc8f8f");
    COLORS.put("darkkhaki", "bdb76b");
    COLORS.put("silver", "c0c0c0");
    COLORS.put("mediumvioletred", "c71585");
    COLORS.put("indianred", "cd5c5c");
    COLORS.put("peru", "cd853f");
    COLORS.put("chocolate", "d2691e");
    COLORS.put("tan", "d2b48c");
    COLORS.put("lightgray", "d3d3d3");
    COLORS.put("lightgrey", "d3d3d3");
    COLORS.put("thistle", "d8bfd8");
    COLORS.put("goldenrod", "daa520");
    COLORS.put("orchid", "da70d6");
    COLORS.put("palevioletred", "db7093");
    COLORS.put("crimson", "dc143c");
    COLORS.put("gainsboro", "dcdcdc");
    COLORS.put("plum", "dda0dd");
    COLORS.put("burlywood", "deb887");
    COLORS.put("lightcyan", "e0ffff");
    COLORS.put("lavender", "e6e6fa");
    COLORS.put("darksalmon", "e9967a");
    COLORS.put("palegoldenrod", "eee8aa");
    COLORS.put("violet", "ee82ee");
    COLORS.put("azure", "f0ffff");
    COLORS.put("honeydew", "f0fff0");
    COLORS.put("khaki", "f0e68c");
    COLORS.put("lightcoral", "f08080");
    COLORS.put("sandybrown", "f4a460");
    COLORS.put("beige", "f5f5dc");
    COLORS.put("mintcream", "f5fffa");
    COLORS.put("wheat", "f5deb3");
    COLORS.put("whitesmoke", "f5f5f5");
    COLORS.put("ghostwhite", "f8f8ff");
    COLORS.put("lightgoldenrodyellow", "fafad2");
    COLORS.put("linen", "faf0e6");
    COLORS.put("salmon", "fa8072");
    COLORS.put("oldlace", "fdf5e6");
    COLORS.put("bisque", "ffe4c4");
    COLORS.put("blanchedalmond", "ffebcd");
    COLORS.put("coral", "ff7f50");
    COLORS.put("cornsilk", "fff8dc");
    COLORS.put("darkorange", "ff8c00");
    COLORS.put("deeppink", "ff1493");
    COLORS.put("floralwhite", "fffaf0");
    COLORS.put("fuchsia", "ff00ff");
    COLORS.put("gold", "ffd700");
    COLORS.put("hotpink", "ff69b4");
    COLORS.put("ivory", "fffff0");
    COLORS.put("lavenderblush", "fff0f5");
    COLORS.put("lemonchiffon", "fffacd");
    COLORS.put("lightpink", "ffb6c1");
    COLORS.put("lightsalmon", "ffa07a");
    COLORS.put("lightyellow", "ffffe0");
    COLORS.put("magenta", "ff00ff");
    COLORS.put("mistyrose", "ffe4e1");
    COLORS.put("moccasin", "ffe4b5");
    COLORS.put("navajowhite", "ffdead");
    COLORS.put("orange", "ffa500");
    COLORS.put("orangered", "ff4500");
    COLORS.put("papayawhip", "ffefd5");
    COLORS.put("peachpuff", "ffdab9");
    COLORS.put("pink", "ffc0cb");
    COLORS.put("red", "ff0000");
    COLORS.put("seashell", "fff5ee");
    COLORS.put("snow", "fffafa");
    COLORS.put("tomato", "ff6347");
    COLORS.put("white", "ffffff");
    COLORS.put("yellow", "ffff00");
    COLORS.put("rebeccapurple", "663399");
  }

  public static Color interpret(final String color) {
    final String fixedColor = color.toLowerCase().replace("-", "");

    if (COLORS.containsKey(fixedColor)) {
      return interpretHex(COLORS.get(fixedColor));
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
