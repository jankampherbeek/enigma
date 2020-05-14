/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.astron.assist;

import com.radixpro.enigma.xchg.domain.SimpleTime;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleTimeTest {

   private final int hour = 16;
   private final int minute = 15;
   private final int second = 30;
   private SimpleTime simpleTime;

   @Before
   public void setUp() {
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