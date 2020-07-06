/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.responses;

import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class TetenburgResponseTest {

   private final double longAsc = 222.33;
   private final String resultMsg = "OK";
   private TetenburgResponse response;

   @Before
   public void setUp() throws Exception {
      response = new TetenburgResponse(longAsc, resultMsg);
   }

   @Test
   public void getLongAsc() {
      assertEquals(longAsc, response.getLongAsc(), DELTA_8_POS);
   }

   @Test
   public void getResultMsg() {
      assertEquals(resultMsg, response.getResultMsg());
   }
}