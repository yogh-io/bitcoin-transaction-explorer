package com.yoghurt.crypto.transactions.shared.util;

public class Str {

	private static int[]delta;

	public static byte[] toBytes(char[] inputChars) {
		byte[] out = new byte[inputChars.length];
		delta = new int[inputChars.length];
		for (int i = 0; i < inputChars.length; i++) {
			out[i] = (byte) inputChars[i];
			delta[i] = (int) inputChars[i] - out[i];
		}
		return out;
	}

	public static char[] toChars(byte[] in) {
		if (in == null) {
			return new char[]{};
		}
		char[] out = new char[in.length];
		for (int i = 0; i < in.length; i++) {
			if (delta == null) { // we aren't handling encrypt/decrypt of multibyte characters, because we haven't been called with toBytes 
				out[i] = (char) in[i];
			} else {
				if (i < delta.length) {
					out[i] = (char) (in[i] + delta[i]);
				} else {
					out[i] = (char) in[i];
				}
			}
		}
		return out;
	}
	
	public static String toString(byte[] in)
	{
		return new String(toChars(in));
	}
	
	public static byte[] fromString(String in)
	{
		return toBytes(in.toCharArray());
	}

}
