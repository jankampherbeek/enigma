/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.be.handlers.SecundaryDateHandler;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_5_POS;
import static org.junit.Assert.assertEquals;

public class SecundaryDateHandlerTest {

   private DateTimeJulian birthDateTime;
   private DateTimeJulian eventDateTime;
   private SecundaryDateHandler handler;


   @Before
   public void setUp() {
      birthDateTime = new DateTimeJulian(2451544.5, "G");    // 2000/1/1
      eventDateTime = new DateTimeJulian(2455197.5, "G");   // 2010/1/1

//2,451,544.5         2,455,197.5	noon on January 1. 4713 BC

//      birthDateTime =    constructDateTime(new SimpleDate(2000, 1, 1, true),
//            new SimpleTime(0, 0, 0));
//      eventDateTime = constructDateTime(new SimpleDate(2010, 1, 1, true),
//            new SimpleTime(0, 0, 0));       // 3650 days + 3 leapdays = 3653 days
      // 3653 / 365.24219 = 10.001582785
      // 10 days, 0 h, 2 m, 17 s
      handler = new SecundaryDateHandler();
   }

   @Test
   public void calcSecundaryDate() {
      DateTimeJulian result = handler.calcSecundaryDate(birthDateTime, eventDateTime);
      assertEquals(2451554.501585648148148, result.getJd(), DELTA_5_POS);    // 2000/1/11 0:02:17
   }

   //Expected :2451554,501585648
   //Actual   :2451554,5015827855  0,25 sec verschil

}