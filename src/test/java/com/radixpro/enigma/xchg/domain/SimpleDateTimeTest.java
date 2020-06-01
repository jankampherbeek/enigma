/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleDateTimeTest {

   private SimpleDate simpleDate;
   private SimpleTime simpleTime;
   private SimpleDateTime dateTime;

   @Before
   public void setUp() {
      simpleDate = new SimpleDate(1953, 1, 29, true);
      simpleTime = new SimpleTime(8, 37, 30);
      dateTime = new SimpleDateTime(simpleDate, simpleTime);
   }

   @Test
   public void getSimpleDate() {
      assertEquals(simpleDate, dateTime.getDate());
   }

   @Test
   public void getSimpleTime() {
      assertEquals(simpleTime, dateTime.getTime());
   }

}