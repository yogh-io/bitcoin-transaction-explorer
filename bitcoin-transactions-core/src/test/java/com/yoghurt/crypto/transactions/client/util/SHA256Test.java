package com.yoghurt.crypto.transactions.client.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import com.yoghurt.crypto.transactions.client.util.crypto.SHA256;

public class SHA256Test {
  @Test
  public void testSHA256() throws DecoderException {
//    compareDigestResultEquals("", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
//
//    Assert.assertArrayEquals(
//        getDoubleDigest(
//            "01000000015A53E9927B4BE7F1C0299CCFA30057B26C93F8665DCC97CFFA59CE8A5212F117000000006B483045022100DC97F68A4A59E38602D81CA6AED17B7E248838EE9CDE9E35655ED3F27332F4B002207A14811BFAA9049DAB0655D02096623A79BBDE4958E06E6475112DA69C8BC19D012102C0658CE4FBB344DCBA0FCF98A8108404EC12922E613E4A7A9FE5314C5D9E3725FFFFFFFF02C074400D000000001976A914EAFCCCAFDDD6E2D52C4E44465389A4A218AE7B9588ACB81AA104000000001976A9147EBD755C62A1AB96CD10D012A70158848B95B62D88AC00000000"),
//        Hex.decodeHex("89e27ec73aa8646f9d4f9758ee0476297653c14ca9d6d3d56035235a19078f85".toCharArray()));
  }

  private void compareDigestResultEquals(final String hexInput, final String expectedHexOutput) throws DecoderException {
    final byte[] digest = getDigest(hexInput);

    Assert.assertArrayEquals(digest, Hex.decodeHex(expectedHexOutput.toCharArray()));
  }

  private byte[] getDigest(final String hexInput) throws DecoderException {
    final SHA256 digest = new SHA256();
    digest.feed(Hex.decodeHex(hexInput.toCharArray()));
    return digest.finish();
  }

  private byte[] getDoubleDigest(final String hexInput) throws DecoderException {
    final SHA256 digest = new SHA256();
    digest.feed(Hex.decodeHex(hexInput.toCharArray()));

    final SHA256 digest2 = new SHA256();
    digest2.feed(digest.finish());

    return digest2.finish();
  }
}
