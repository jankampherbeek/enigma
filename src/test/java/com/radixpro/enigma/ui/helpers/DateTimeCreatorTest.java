/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.helpers;

import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.references.TimeZones;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class DateTimeCreatorTest {


   @Test
   public void createDateTime() {
      String dateText = "1953/01/29";
      String timeText = "8:37:30";
      TimeZones zone = TimeZones.CET;
      boolean dst = false;
      String cal = "G";
      double offsetLmt = 0.0;
      DateTimeJulian dateTime = DateTimeCreator.INSTANCE.createDateTimeJulian(dateText, cal, timeText, zone, dst, offsetLmt);
      assertEquals(2434406.8177083335, dateTime.getJd(), DELTA_8_POS);
      assertEquals(cal, dateTime.getCalendar());
   }

   @Test
   public void createDateTimeUsingLmt() {
      String dateText = "1953/01/29";
      String timeText = "8:37:30";
      TimeZones zone = TimeZones.LMT;
      boolean dst = false;
      String cal = "G";
      double offsetLmt = 1.0;
      DateTimeJulian dateTime = DateTimeCreator.INSTANCE.createDateTimeJulian(dateText, cal, timeText, zone, dst, offsetLmt);
      assertEquals(2434406.8177083335, dateTime.getJd(), DELTA_8_POS);
      assertEquals(cal, dateTime.getCalendar());
   }

   @Test
   public void createDateTimeUsingDst() {
      String dateText = "1953/01/29";
      String timeText = "9:37:30";
      TimeZones zone = TimeZones.CET;
      boolean dst = true;
      String cal = "G";
      double offsetLmt = 0.0;
      DateTimeJulian dateTime = DateTimeCreator.INSTANCE.createDateTimeJulian(dateText, cal, timeText, zone, dst, offsetLmt);
      assertEquals(2434406.8177083335, dateTime.getJd(), DELTA_8_POS);
      assertEquals(cal, dateTime.getCalendar());
   }

}