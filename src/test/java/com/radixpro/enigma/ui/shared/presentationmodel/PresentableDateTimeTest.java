/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.xchg.domain.*;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class PresentableDateTimeTest {

   private final int year = 2020;
   private final int month = 2;
   private final int day = 13;
   private final int hour = 22;
   private final int minute = 39;
   private final int second = 5;
   private final boolean cal = true;
   private final boolean dst = false;
   private final double offsetForLmt = 0.0;
   private final String expectedDate = "2020/02/13 G";
   private final String expectedTime = "22:39:05 No DST +00:00: UT/Coordinated Universal Time, GMT/Greenwich Mean Time";
   private final TimeZones timeZone = TimeZones.UT;
   private SimpleDateTime dateTime;
   private FullDateTime fullDateTime;
   private PresentableDateTime presDateTime;

   @Before
   public void setUp() throws Exception {
      dateTime = new SimpleDateTime(new SimpleDate(year, month, day, cal), new SimpleTime(hour, minute, second));
      fullDateTime = new FullDateTime(dateTime, timeZone, dst, offsetForLmt);
      presDateTime = new PresentableDateTime(fullDateTime);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getDate() {
      assertEquals(expectedDate, presDateTime.getDate());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getTime() {
      assertEquals(expectedTime, presDateTime.getTime());
   }
}