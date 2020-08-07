/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.datetime.SimpleDate;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.domain.datetime.SimpleTime;
import com.radixpro.enigma.references.TimeZones;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class PresentableDateTimeTest {

   private final TimeZones timeZone = TimeZones.UT;
   private PresentableDateTime presDateTime;

   @Before
   public void setUp() {
      boolean cal = true;
      int second = 5;
      int minute = 39;
      int hour = 22;
      int day = 13;
      int month = 2;
      int year = 2020;
      SimpleDateTime dateTime = new SimpleDateTime(new SimpleDate(year, month, day, cal), new SimpleTime(hour, minute, second));
      double offsetForLmt = 0.0;
      boolean dst = false;
      FullDateTime fullDateTime = new FullDateTime(dateTime, timeZone, dst, offsetForLmt);
      presDateTime = new PresentableDateTime(fullDateTime);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getDate() {
      String expectedDate = "2020/02/13 G";
      assertEquals(expectedDate, presDateTime.getDate());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getTime() {
      String expectedTime = "22:39:05 No DST +00:00: UT/Coordinated Universal Time, GMT/Greenwich Mean Time";
      assertEquals(expectedTime, presDateTime.getTime());
   }
}