/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleDateTest {

   private final int year = 2020;
   private final int month = 2;
   private final int day = 18;
   private final boolean gregorian = true;
   private SimpleDate simpleDate;

   @Before
   public void setUp() throws Exception {
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