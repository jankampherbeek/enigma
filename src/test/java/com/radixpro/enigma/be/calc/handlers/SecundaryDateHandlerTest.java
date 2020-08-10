/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.be.handlers.SecundaryDateHandler;
import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.datetime.SimpleDate;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.domain.datetime.SimpleTime;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.testsupport.DbTestSupport;
import com.radixpro.enigma.xchg.domain.Location;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SecundaryDateHandlerTest {

   private FullDateTime birthDateTime;
   private FullDateTime eventDateTime;
   private Location location;
   private SecundaryDateHandler handler;


   @Before
   public void setUp() {
      DbTestSupport.useDb();
      birthDateTime = constructDateTime(new SimpleDate(2000, 1, 1, true),
            new SimpleTime(0, 0, 0));
      eventDateTime = constructDateTime(new SimpleDate(2010, 1, 1, true),
            new SimpleTime(0, 0, 0));       // 3650 days + 3 leapdays = 3653 days
      // 3653 / 365.24219 = 10.001582785
      // 10 days, 0 h, 2 m, 17 s
      handler = new SecundaryDateHandler();
   }

   @Test
   public void calcSecundaryDate() {
      FullDateTime result = handler.calcSecundaryDate(birthDateTime, eventDateTime);
      assertEquals(11, result.getSimpleDateTime().getDate().getDay());
      assertEquals(1, result.getSimpleDateTime().getDate().getMonth());
      assertEquals(2000, result.getSimpleDateTime().getDate().getYear());
      assertEquals(0, result.getSimpleDateTime().getTime().getHour());
      assertEquals(2, result.getSimpleDateTime().getTime().getMinute());
      assertEquals(17, result.getSimpleDateTime().getTime().getSecond());
   }

   private FullDateTime constructDateTime(SimpleDate simpleDate, SimpleTime simpleTime) {
      SimpleDateTime simpleDateTime = new SimpleDateTime(simpleDate, simpleTime);
      return new FullDateTime(simpleDateTime, TimeZones.UT, false, 0.0);
   }

}