/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared.converters;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.shared.exceptions.InputDataException;
import com.radixpro.enigma.testsupport.AppScopeFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Csv2FullDateTimeConverterTest {

   private final String dateTxt = "1953/1/29";
   private final String timeTxt = "8:37:30";
   private final String calendar = "G";
   private final String dst = "N";
   private final String zone = "CET";
   private Csv2FullDateTimeConverter converter;

   @Before
   public void setUp() throws Exception {
      converter = new Csv2FullDateTimeConverter();
      AppScopeFactory.getAppScope().getRosetta();
   }

   @Test
   public void convertHappyFlow() throws InputDataException {
      FullDateTime fDateTime = converter.convert(dateTxt, timeTxt, calendar, zone, dst);
      assertEquals(1953, fDateTime.getSimpleDateTime().getDate().getYear());
      assertEquals(1, fDateTime.getSimpleDateTime().getDate().getMonth());
      assertEquals(29, fDateTime.getSimpleDateTime().getDate().getDay());
      assertEquals(8, fDateTime.getSimpleDateTime().getTime().getHour());
      assertEquals(37, fDateTime.getSimpleDateTime().getTime().getMinute());
      assertEquals(30, fDateTime.getSimpleDateTime().getTime().getSecond());
      assertTrue(fDateTime.getSimpleDateTime().getDate().isGregorian());
      assertFalse(fDateTime.isDst());
      assertEquals(TimeZones.CET, fDateTime.getTimeZone());
   }

   @Test(expected = InputDataException.class)
   public void convertDateNumericError() throws InputDataException {
      converter.convert("1953/a/29", timeTxt, calendar, zone, dst);
   }

   @Test(expected = InputDataException.class)
   public void convertTimeNumericError() throws InputDataException {
      converter.convert(dateTxt, "a:b", calendar, zone, dst);
   }

   @Test(expected = InputDataException.class)
   public void convertZoneError() throws InputDataException {
      converter.convert(dateTxt, timeTxt, calendar, "XXX", dst);
   }

}