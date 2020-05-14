/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.astron.assist;

import com.radixpro.enigma.xchg.domain.SimpleDate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleDateTest {

   private final int year = 2019;
   private final int month = 12;
   private final int day = 27;
   private final boolean gregorian = true;
   private SimpleDate simpleDate;

   @Before
   public void setUp() {
      simpleDate = new SimpleDate(year, month, day, gregorian);
   }

   @Test
   public void getYear() {
      assertEquals(year, simpleDate.getYear());
   }

   @Test
   public void getMonth() {
      assertEquals(month, simpleDate.getMonth());
   }

   @Test
   public void getDay() {
      assertEquals(day, simpleDate.getDay());
   }

   @Test
   public void isGregorian() {
      assertEquals(gregorian, simpleDate.isGregorian());
   }
}