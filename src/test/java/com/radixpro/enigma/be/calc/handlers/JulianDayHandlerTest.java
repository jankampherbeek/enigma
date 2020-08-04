/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.domain.datetime.SimpleDate;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.domain.datetime.SimpleTime;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class JulianDayHandlerTest {

   private final double jdEt = 2451544.5007428704;
   private final double jdUt = 2451544.500004102;
   private SimpleDateTime dateTime;
   private JulianDayHandler julianDayHandler;

   @Before
   public void setUp() {
      SimpleDate date = new SimpleDate(2000, 1, 1, true);
      SimpleTime time = new SimpleTime(0, 0, 0);
      dateTime = new SimpleDateTime(date, time);
      julianDayHandler = CaHandlersFactory.getJulianDayHandler();
   }

   @Test
   public void calculateJdNrEt() {
      assertEquals(jdEt, julianDayHandler.calculateJdNr(dateTime, "et"), DELTA_8_POS);
   }

   @Test
   public void calculateJdNrUt() {
      assertEquals(jdUt, julianDayHandler.calculateJdNr(dateTime, "ut"), DELTA_8_POS);
   }

   @Test
   public void dateTimeFromJdForEt() {
      SimpleDateTime result = julianDayHandler.dateTimeFromJd(jdEt, false, "et");
      assertEquals(1, result.getDate().getDay());
      assertEquals(0, result.getTime().getSecond());
   }

   @Test
   public void dateTimeFromJdForUt() {
      SimpleDateTime result = julianDayHandler.dateTimeFromJd(jdUt, false, "ut");
      assertEquals(1, result.getDate().getDay());
      assertEquals(0, result.getTime().getSecond());
   }

}