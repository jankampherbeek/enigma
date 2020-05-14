/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleTimeTest {

   private final int hour = 10;
   private final int minute = 30;
   private final int second = 38;
   private SimpleTime simpleTime;

   @Before
   public void setUp() throws Exception {
      simpleTime = new SimpleTime(hour, minute, second);
   }

   @Test
   public void getHour() {
      assertEquals(hour, simpleTime.getHour());
   }

   @Test
   public void getMinute() {
      assertEquals(minute, simpleTime.getMinute());
   }

   @Test
   public void getSecond() {
      assertEquals(second, simpleTime.getSecond());
   }

}