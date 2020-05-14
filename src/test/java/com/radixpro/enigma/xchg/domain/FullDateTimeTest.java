/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
public class FullDateTimeTest {

   private static final double DELTA = 0.00000001;
   //   @Mock
   private SimpleDateTime simpleDateTimeMock;
   //   @Mock
   private SimpleTime simpleTimeMock;
   //   @Mock
   private SimpleDate simpleDateMock;
   private TimeZones timeZone = TimeZones.LMT;
   private boolean dst = false;
   private double offsetForLmt = 0.115;
   private FullDateTime fullDateTime;

   @Before
   public void setUp() {
      when(simpleDateMock.getYear()).thenReturn(2020);
      when(simpleDateMock.getMonth()).thenReturn(3);
      when(simpleDateMock.getDay()).thenReturn(3);
      when(simpleDateMock.isGregorian()).thenReturn(true);
      when(simpleTimeMock.getHour()).thenReturn(15);
      when(simpleTimeMock.getMinute()).thenReturn(0);
      when(simpleTimeMock.getSecond()).thenReturn(0);
      when(simpleDateTimeMock.getTime()).thenReturn(simpleTimeMock);
      when(simpleDateTimeMock.getDate()).thenReturn(simpleDateMock);
      fullDateTime = new FullDateTime(simpleDateTimeMock, timeZone, dst, offsetForLmt);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getDateTime() {
      assertEquals(simpleDateTimeMock, fullDateTime.getSimpleDateTime());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getTimeZone() {
      assertEquals(timeZone, fullDateTime.getTimeZone());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void isDst() {
      assertEquals(dst, fullDateTime.isDst());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getOffsetForLmt() {
      assertEquals(offsetForLmt, fullDateTime.getOffsetForLmt(), DELTA);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getJdUtHappyFlow() {
      fullDateTime = new FullDateTime(simpleDateTimeMock, TimeZones.UT, false, 0.0);
      assertEquals(2458912.125, fullDateTime.getJdUt(), DELTA);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getJdEtHappyFlow() {
      fullDateTime = new FullDateTime(simpleDateTimeMock, TimeZones.UT, false, 0.0);
      assertEquals(2458912.1258256687, fullDateTime.getJdEt(), DELTA);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getJdUtWithLmt() {
      fullDateTime = new FullDateTime(simpleDateTimeMock, TimeZones.LMT, false, -5.0);
      assertEquals(2458912.3333333335, fullDateTime.getJdUt(), DELTA);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getJdEtWithLmt() {
      fullDateTime = new FullDateTime(simpleDateTimeMock, TimeZones.LMT, false, -5.0);
      assertEquals(2458912.334159002, fullDateTime.getJdEt(), DELTA);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getJdUtWithDst() {
      fullDateTime = new FullDateTime(simpleDateTimeMock, TimeZones.UT, true, 0.0);
      assertEquals(2458912.0833333335, fullDateTime.getJdUt(), DELTA);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getJdEtWithDst() {
      fullDateTime = new FullDateTime(simpleDateTimeMock, TimeZones.UT, true, 0.0);
      assertEquals(2458912.084159002, fullDateTime.getJdEt(), DELTA);
   }


   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getJdUtPreviousDay() {
      when(simpleTimeMock.getHour()).thenReturn(3);
      fullDateTime = new FullDateTime(simpleDateTimeMock, TimeZones.EST, false, 0.0);
      assertEquals(2458911.8333333335, fullDateTime.getJdUt(), DELTA);  // offset -5
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getJdEtPreviousDay() {
      when(simpleTimeMock.getHour()).thenReturn(3);  // offset -5
      fullDateTime = new FullDateTime(simpleDateTimeMock, TimeZones.EST, false, 0.0);
      assertEquals(2458911.834158977, fullDateTime.getJdEt(), DELTA);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getJdUtNextDay() {
      when(simpleTimeMock.getHour()).thenReturn(23);
      fullDateTime = new FullDateTime(simpleDateTimeMock, TimeZones.ICT, false, 0.0);
      assertEquals(2458912.166666667, fullDateTime.getJdUt(), DELTA);  // offset + 7.0
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getJdEtNextDay() {
      when(simpleTimeMock.getHour()).thenReturn(23);
      fullDateTime = new FullDateTime(simpleDateTimeMock, TimeZones.ICT, false, 0.0);
      assertEquals(2458912.167492352, fullDateTime.getJdEt(), DELTA);
   }

}