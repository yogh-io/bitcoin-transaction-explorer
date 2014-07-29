package com.yoghurt.crypto.transactions.client.util;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.domain.transaction.RawTransactionContainer;
import com.yoghurt.crypto.transactions.client.domain.transaction.RawTransactionPart;
import com.yoghurt.crypto.transactions.client.domain.transaction.Transaction;

public class TransactionDecodeUtilTest {
  private static final String TEST_TX = "01000000016d5412cdc802cee86b4f939ed7fc77c158193ce744f1117b5c6b67a4d70c046b010000006c493046022100be69797cf5d784412b1258256eb657c191a04893479dfa2ae5c7f2088c7adbe0022100e6b000bd633b286ed1b9bc7682fe753d9fdad61fbe5da2a6e9444198e33a670f012102f0e17f9afb1dca5ab9058b7021ba9fcbedecf4fac0f1c9e0fd96c4fdc200c1c2ffffffff0245a87edb080000001976a9147d4e6d55e1dffb0df85f509343451d170d14755188ac60e31600000000001976a9143bc576e6960a9d45201ba5087e39224d0a05a07988ac00000000";

  @Test
  public void testConsistency() {
    final Transaction tx = TransactionParseUtil.parseTransactionHex(TEST_TX);

    final RawTransactionContainer reencoded = TransactionEncodeUtil.encodeTransaction(tx);

    final ArrayList<Byte> bytes = new ArrayList<Byte>();
    for (final RawTransactionPart part : reencoded) {
      for (final byte bite : part.getBytes()) {
        bytes.add(bite);
      }
    }

    final byte[] primitiveBytes = new byte[bytes.size()];
    int i = 0;
    for(final byte bite : bytes) {
      primitiveBytes[i++] = bite;
    }

    final byte[] encode = Hex.encode(primitiveBytes);

    Assert.assertEquals(TEST_TX, new String(encode));
  }
}
