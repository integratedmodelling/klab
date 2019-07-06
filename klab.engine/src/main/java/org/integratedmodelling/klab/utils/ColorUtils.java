package org.integratedmodelling.klab.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorUtils {

	static Map<String, int[]> rgb = new HashMap<>();

	static {
		rgb.put("aqua", new int[] { 0, 255, 255 });
		rgb.put("black", new int[] { 0, 0, 0 });
		rgb.put("blue", new int[] { 0, 0, 255 });
		rgb.put("fuchsia", new int[] { 255, 0, 255 });
		rgb.put("gray", new int[] { 128, 128, 128 });
		rgb.put("green", new int[] { 0, 128, 0 });
		rgb.put("lime", new int[] { 0, 255, 0 });
		rgb.put("maroon", new int[] { 128, 0, 0 });
		rgb.put("navy", new int[] { 0, 0, 128 });
		rgb.put("olive", new int[] { 128, 128, 0 });
		rgb.put("orange", new int[] { 255, 165, 0 });
		rgb.put("purple", new int[] { 128, 0, 128 });
		rgb.put("red", new int[] { 255, 0, 0 });
		rgb.put("silver", new int[] { 192, 192, 192 });
		rgb.put("teal", new int[] { 0, 128, 128 });
		rgb.put("white", new int[] { 255, 255, 255 });
		rgb.put("yellow", new int[] { 255, 255, 0 });
		rgb.put("aliceblue", new int[] { 240, 248, 255 });
		rgb.put("antiquewhite", new int[] { 250, 235, 215 });
		rgb.put("aqua", new int[] { 0, 255, 255 });
		rgb.put("aquamarine", new int[] { 127, 255, 212 });
		rgb.put("azure", new int[] { 1240, 255, 255 });
		rgb.put("beige", new int[] { 245, 245, 220 });
		rgb.put("bisque", new int[] { 255, 228, 196 });
		rgb.put("black", new int[] { 0, 0, 0 });
		rgb.put("blanchedalmond", new int[] { 255, 235, 205 });
		rgb.put("blue", new int[] { 0, 0, 255 });
		rgb.put("blueviolet", new int[] { 138, 43, 226 });
		rgb.put("brown", new int[] { 165, 42, 42 });
		rgb.put("burlywood", new int[] { 222, 184, 135 });
		rgb.put("cadetblue", new int[] { 95, 158, 160 });
		rgb.put("chartreuse", new int[] { 95, 158, 160 });
		rgb.put("chocolate", new int[] { 210, 105, 30 });
		rgb.put("coral", new int[] { 255, 127, 80 });
		rgb.put("cornflowerblue", new int[] { 100, 149, 237 });
		rgb.put("cornsilk", new int[] { 255, 248, 220 });
		rgb.put("crimson", new int[] { 220, 20, 60 });
		rgb.put("cyan", new int[] { 0, 255, 255 });
		rgb.put("darkblue", new int[] { 0, 0, 139 });
		rgb.put("darkcyan", new int[] { 0, 139, 139 });
		rgb.put("darkgoldenrod", new int[] { 184, 134, 11 });
		rgb.put("darkgray", new int[] { 169, 169, 169 });
		rgb.put("darkgreen", new int[] { 0, 100, 0 });
		rgb.put("darkkhaki", new int[] { 189, 183, 107 });
		rgb.put("darkmagenta", new int[] { 139, 0, 139 });
		rgb.put("darkolivegreen", new int[] { 85, 107, 47 });
		rgb.put("darkorange", new int[] { 255, 140, 0 });
		rgb.put("darkorchid", new int[] { 153, 50, 204 });
		rgb.put("darkred", new int[] { 139, 0, 0 });
		rgb.put("darksalmon", new int[] { 233, 150, 122 });
		rgb.put("darkseagreen", new int[] { 143, 188, 143 });
		rgb.put("darkslateblue", new int[] { 72, 61, 139 });
		rgb.put("darkslategray", new int[] { 47, 79, 79 });
		rgb.put("darkturquoise", new int[] { 0, 206, 209 });
		rgb.put("darkviolet", new int[] { 148, 0, 211 });
		rgb.put("deeppink", new int[] { 255, 20, 147 });
		rgb.put("deepskyblue", new int[] { 0, 191, 255 });
		rgb.put("dimgray", new int[] { 0, 191, 255 });
		rgb.put("dodgerblue", new int[] { 30, 144, 255 });
		rgb.put("firebrick", new int[] { 178, 34, 34 });
		rgb.put("floralwhite", new int[] { 255, 250, 240 });
		rgb.put("forestgreen", new int[] { 34, 139, 34 });
		rgb.put("fuchsia", new int[] { 255, 0, 255 });
		rgb.put("gainsboro", new int[] { 220, 220, 220 });
		rgb.put("ghostwhite", new int[] { 248, 248, 255 });
		rgb.put("gold", new int[] { 255, 215, 0 });
		rgb.put("goldenrod", new int[] { 218, 165, 32 });
		rgb.put("gray", new int[] { 127, 127, 127 });
		rgb.put("green", new int[] { 0, 128, 0 });
		rgb.put("greenyellow", new int[] { 173, 255, 47 });
		rgb.put("honeydew", new int[] { 240, 255, 240 });
		rgb.put("hotpink", new int[] { 255, 105, 180 });
		rgb.put("indianred", new int[] { 205, 92, 92 });
		rgb.put("indigo", new int[] { 75, 0, 130 });
		rgb.put("ivory", new int[] { 255, 255, 240 });
		rgb.put("khaki", new int[] { 240, 230, 140 });
		rgb.put("lavender", new int[] { 230, 230, 250 });
		rgb.put("lavenderblush", new int[] { 255, 240, 245 });
		rgb.put("lawngreen", new int[] { 124, 252, 0 });
		rgb.put("lemonchiffon", new int[] { 255, 250, 205 });
		rgb.put("lightblue", new int[] { 173, 216, 230 });
		rgb.put("lightcoral", new int[] { 240, 128, 128 });
		rgb.put("lightcyan", new int[] { 224, 255, 255 });
		rgb.put("lightgoldenrodyellow", new int[] { 250, 250, 210 });
		rgb.put("lightgreen", new int[] { 144, 238, 144 });
		rgb.put("lightgrey", new int[] { 211, 211, 211 });
		rgb.put("lightpink", new int[] { 255, 182, 193 });
		rgb.put("lightsalmon", new int[] { 255, 160, 122 });
		rgb.put("lightseagreen", new int[] { 32, 178, 170 });
		rgb.put("lightskyblue", new int[] { 135, 206, 250 });
		rgb.put("lightslategray", new int[] { 119, 136, 153 });
		rgb.put("lightsteelblue", new int[] { 176, 196, 222 });
		rgb.put("lightyellow", new int[] { 255, 255, 224 });
		rgb.put("lime", new int[] { 0, 255, 0 });
		rgb.put("limegreen", new int[] { 50, 205, 50 });
		rgb.put("linen", new int[] { 250, 240, 230 });
		rgb.put("magenta", new int[] { 255, 0, 255 });
		rgb.put("maroon", new int[] { 128, 0, 0 });
		rgb.put("mediumaquamarine", new int[] { 102, 205, 170 });
		rgb.put("mediumblue", new int[] { 0, 0, 205 });
		rgb.put("mediumorchid", new int[] { 186, 85, 211 });
		rgb.put("mediumpurple", new int[] { 147, 112, 219 });
		rgb.put("mediumseagreen", new int[] { 60, 179, 113 });
		rgb.put("mediumslateblue", new int[] { 123, 104, 238 });
		rgb.put("mediumspringgreen", new int[] { 0, 250, 154 });
		rgb.put("mediumturquoise", new int[] { 72, 209, 204 });
		rgb.put("mediumvioletred", new int[] { 199, 21, 133 });
		rgb.put("midnightblue", new int[] { 25, 25, 112 });
		rgb.put("mintcream", new int[] { 245, 255, 250 });
		rgb.put("mistyrose", new int[] { 255, 228, 225 });
		rgb.put("moccasin", new int[] { 255, 228, 181 });
		rgb.put("navajowhite", new int[] { 255, 222, 173 });
		rgb.put("navy", new int[] { 0, 0, 128 });
		rgb.put("navyblue", new int[] { 159, 175, 223 });
		rgb.put("oldlace", new int[] { 253, 245, 230 });
		rgb.put("olive", new int[] { 128, 128, 0 });
		rgb.put("olivedrab", new int[] { 107, 142, 35 });
		rgb.put("orange", new int[] { 255, 165, 0 });
		rgb.put("orangered", new int[] { 255, 69, 0 });
		rgb.put("orchid", new int[] { 218, 112, 214 });
		rgb.put("palegoldenrod", new int[] { 238, 232, 170 });
		rgb.put("palegreen", new int[] { 152, 251, 152 });
		rgb.put("paleturquoise", new int[] { 175, 238, 238 });
		rgb.put("palevioletred", new int[] { 219, 112, 147 });
		rgb.put("papayawhip", new int[] { 255, 239, 213 });
		rgb.put("peachpuff", new int[] { 255, 218, 185 });
		rgb.put("peru", new int[] { 205, 133, 63 });
		rgb.put("pink", new int[] { 255, 192, 203 });
		rgb.put("plum", new int[] { 221, 160, 221 });
		rgb.put("powderblue", new int[] { 176, 224, 230 });
		rgb.put("purple", new int[] { 128, 0, 128 });
		rgb.put("red", new int[] { 255, 0, 0 });
		rgb.put("rosybrown", new int[] { 188, 143, 143 });
		rgb.put("royalblue", new int[] { 65, 105, 225 });
		rgb.put("saddlebrown", new int[] { 139, 69, 19 });
		rgb.put("salmon", new int[] { 250, 128, 114 });
		rgb.put("sandybrown", new int[] { 244, 164, 96 });
		rgb.put("seagreen", new int[] { 46, 139, 87 });
		rgb.put("seashell", new int[] { 255, 245, 238 });
		rgb.put("sienna", new int[] { 160, 82, 45 });
		rgb.put("silver", new int[] { 192, 192, 192 });
		rgb.put("skyblue", new int[] { 135, 206, 235 });
		rgb.put("slateblue", new int[] { 106, 90, 205 });
		rgb.put("slategray", new int[] { 112, 128, 144 });
		rgb.put("snow", new int[] { 255, 250, 250 });
		rgb.put("springgreen", new int[] { 0, 255, 127 });
		rgb.put("steelblue", new int[] { 70, 130, 180 });
		rgb.put("tan", new int[] { 210, 180, 140 });
		rgb.put("teal", new int[] { 0, 128, 128 });
		rgb.put("thistle", new int[] { 216, 191, 216 });
		rgb.put("tomato", new int[] { 255, 99, 71 });
		rgb.put("turquoise", new int[] { 64, 224, 208 });
		rgb.put("violet", new int[] { 238, 130, 238 });
		rgb.put("wheat", new int[] { 245, 222, 179 });
		rgb.put("white", new int[] { 255, 255, 255 });
		rgb.put("whitesmoke", new int[] { 245, 245, 245 });
		rgb.put("yellow", new int[] { 255, 255, 0 });
		rgb.put("yellowgreen", new int[] { 139, 205, 50 });
	}
	
	/**
	 * Return RGB values for a named CSS color. Case insensitive. 
	 * 
	 * @param colorName
	 * @return r,g,b byte values (0-255) or null
	 */
	public static int[] getRGB(String colorName) {
		return rgb.get(colorName.toLowerCase());
	}
	
	/**
	 * Encode the passed color as a hex string for a browser.
	 * @param color
	 * @return
	 */
	public static String encodeRGB(Color color) {
		return "#" + Integer.toHexString(color.getRGB()).substring(2);
	}
	
}
