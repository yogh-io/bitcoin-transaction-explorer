package com.yoghurt.crypto.transactions.client.util.crypto;

/**
 * Class that performs the SHA-256 digest operation.
 * 
 * This class may not be the fastest one. The main goal of this class is to be
 * compatible rather than fast. Since the code may be used in GWT, we need to
 * take care of some extra checks to ensure that the specification is followed
 * carefully.
 * 
 * @author byo
 * 
 */
public class SHA256 {

  private static int[] K = { 0x428A2F98, 0x71374491, 0xB5C0FBCF, 0xE9B5DBA5,
    0x3956C25B, 0x59F111F1, 0x923F82A4, 0xAB1C5ED5, 0xD807AA98,
    0x12835B01, 0x243185BE, 0x550C7DC3, 0x72BE5D74, 0x80DEB1FE,
    0x9BDC06A7, 0xC19BF174, 0xE49B69C1, 0xEFBE4786, 0x0FC19DC6,
    0x240CA1CC, 0x2DE92C6F, 0x4A7484AA, 0x5CB0A9DC, 0x76F988DA,
    0x983E5152, 0xA831C66D, 0xB00327C8, 0xBF597FC7, 0xC6E00BF3,
    0xD5A79147, 0x06CA6351, 0x14292967, 0x27B70A85, 0x2E1B2138,
    0x4D2C6DFC, 0x53380D13, 0x650A7354, 0x766A0ABB, 0x81C2C92E,
    0x92722C85, 0xA2BFE8A1, 0xA81A664B, 0xC24B8B70, 0xC76C51A3,
    0xD192E819, 0xD6990624, 0xF40E3585, 0x106AA070, 0x19A4C116,
    0x1E376C08, 0x2748774C, 0x34B0BCB5, 0x391C0CB3, 0x4ED8AA4A,
    0x5B9CCA4F, 0x682E6FF3, 0x748F82EE, 0x78A5636F, 0x84C87814,
    0x8CC70208, 0x90BEFFFA, 0xA4506CEB, 0xBEF9A3F7, 0xC67178F2 };

  private static final int BLOCK_SIZE = 64;
  private static final int LAST_SIZE = 56;

  private final int[] state = { 0x6A09E667, 0xBB67AE85, 0x3C6EF372, 0xA54FF53A,
      0x510E527F, 0x9B05688C, 0x1F83D9AB, 0x5BE0CD19 };
  private final int[] w = new int[BLOCK_SIZE];
  private final byte[] feedBuff = new byte[BLOCK_SIZE];
  private int feedBuffPos = 0;
  private int totalLen = 0;

  /**
   * Safely add two numbers with result clamping (may be needed in
   * gwt-compiled code)
   * 
   * @param x
   *            first value
   * @param y
   *            second value
   * @return clamped sum of values
   */
  private static int safeAdd(final int x, final int y) {
    final int lsw = (x & 0xFFFF) + (y & 0xFFFF);
    final int msw = (x >> 16) + (y >> 16) + (lsw >> 16);
    return msw << 16 | lsw & 0xFFFF;
  }

  /**
   * Store given int value into a buffer
   * 
   * @param buf
   *            output buffer
   * @param pos
   *            position at which we should write the value
   * @param value
   *            value to write
   */
  private static void writeInt(final byte[] buf, final int pos, final int value) {
    buf[pos + 0] = (byte) (value >> 24);
    buf[pos + 1] = (byte) (value >> 16);
    buf[pos + 2] = (byte) (value >> 8);
    buf[pos + 3] = (byte) (value >> 0);
  }

  private static int s(final int x, final int n) {
    return x >>> n | x << 32 - n;
  }

  private static int r(final int x, final int n) {
    return x >>> n;
  }

  private static int ch(final int x, final int y, final int z) {
    return x & y ^ ~x & z;
  }

  private static int maj(final int x, final int y, final int z) {
    return x & y ^ x & z ^ y & z;
  }

  private static int sigma0256(final int x) {
    return s(x, 2) ^ s(x, 13) ^ s(x, 22);
  }

  private static int sigma1256(final int x) {
    return s(x, 6) ^ s(x, 11) ^ s(x, 25);
  }

  private static int gamma0256(final int x) {
    return s(x, 7) ^ s(x, 18) ^ r(x, 3);
  }

  private static int gamma1256(final int x) {
    return s(x, 17) ^ s(x, 19) ^ r(x, 10);
  }

  /**
   * Process single 64-bit block of data
   * 
   * @param m
   *            buffer
   * @param pos
   *            start position within the buffer
   */
  private void processBlock(final byte[] m, int pos) {
    int a = state[0];
    int b = state[1];
    int c = state[2];
    int d = state[3];
    int e = state[4];
    int f = state[5];
    int g = state[6];
    int h = state[7];

    for (int j = 0; j < BLOCK_SIZE; j++, pos += 4) {
      if (j < 16) {
        w[j] = (m[pos + 0] & 0xFF) << 24
            | (m[pos + 1] & 0xFF) << 16
            | (m[pos + 2] & 0xFF) << 8
            | (m[pos + 3] & 0xFF) << 0;
      } else {
        w[j] = safeAdd(safeAdd(safeAdd(gamma1256(w[j - 2]), w[j - 7]),
            gamma0256(w[j - 15])), w[j - 16]);
      }

      final int T1 = safeAdd(safeAdd(safeAdd(safeAdd(h, sigma1256(e)), ch(e, f,
          g)), K[j]), w[j]);
      final int T2 = safeAdd(sigma0256(a), maj(a, b, c));

      h = g;
      g = f;
      f = e;
      e = safeAdd(d, T1);
      d = c;
      c = b;
      b = a;
      a = safeAdd(T1, T2);
    }

    state[0] = safeAdd(a, state[0]);
    state[1] = safeAdd(b, state[1]);
    state[2] = safeAdd(c, state[2]);
    state[3] = safeAdd(d, state[3]);
    state[4] = safeAdd(e, state[4]);
    state[5] = safeAdd(f, state[5]);
    state[6] = safeAdd(g, state[6]);
    state[7] = safeAdd(h, state[7]);

  }

  /**
   * Add block of data into the digest
   * 
   * @param data
   */
  public void feed(final byte[] data) {
    totalLen += data.length;

    int pos = 0;
    int left = data.length;
    while (left > 0) {

      if (feedBuffPos == 0 && left >= BLOCK_SIZE) {

        // Can optimize the large block case
        processBlock(data, pos);
        pos += BLOCK_SIZE;
        left -= BLOCK_SIZE;

      } else {

        final int toCopy = Math.min(left, 64 - feedBuffPos);
        System.arraycopy(data, pos, feedBuff, feedBuffPos, toCopy);
        pos += toCopy;
        left -= toCopy;
        feedBuffPos += toCopy;
        if (feedBuffPos >= BLOCK_SIZE) {
          processBlock(feedBuff, 0);
          feedBuffPos = 0;
        }

      }
    }
  }

  /**
   * Finalize digest generation
   * 
   * @return generated hash
   */
  public byte[] finish() {
    if (feedBuffPos >= LAST_SIZE) {
      feedBuff[feedBuffPos++] = (byte) 0x80;
      while (feedBuffPos < BLOCK_SIZE) {
        feedBuff[feedBuffPos++] = 0x00;
      }
      processBlock(feedBuff, 0);
      feedBuffPos = 0;
    } else {
      feedBuff[feedBuffPos++] = (byte) 0x80;
    }

    while (feedBuffPos < LAST_SIZE) {
      feedBuff[feedBuffPos++] = 0x00;
    }

    // TODO: we need to be able to handle 2GB+ data
    writeInt(feedBuff, LAST_SIZE, /* totalLenHi */0);
    writeInt(feedBuff, LAST_SIZE + 4, totalLen << 3);

    processBlock(feedBuff, 0);

    // Extracting the result
    final byte[] ret = new byte[state.length * 4];
    for (int i = 0; i < state.length; ++i) {
      writeInt(ret, 4 * i, state[i]);
    }

    return ret;
  }

  public int hashLength() {
    return state.length * 4;
  }

  public String name() {
    return "SHA-256";
  }
}