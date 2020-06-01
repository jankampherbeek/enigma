/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.formatters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SexagesimalFormatterTest {

   private static final String DEGREESIGN = "\u00B0";
   private static final String MINUTESIGN = "\u2032";
   private static final String SECONDSIGN = "\u2033";
   private SexagesimalFormatter formatter2;
   private SexagesimalFormatter formatter3;
   private SexagesimalFormatter formatter8;
   private double value2Format;

   @Before
   public void setUp() {
      formatter2 = new SexagesimalFormatter(2);
      formatter3 = new SexagesimalFormatter(3);
      formatter8 = new SexagesimalFormatter(8);
      value2Format = 1.5;
   }

   @Test
   public void formatIntegerPartIs2HappyFlow() {
      assertEquals("01" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN, formatter2.formatDms(value2Format));
   }

   @Test
   public void formatIntegerPartIs3HappyFlow() {
      assertEquals("001" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN, formatter3.formatDms(value2Format));
   }

   @Test
   public void formatIntegerPartIs8HappyFlow() {
      assertEquals("001" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN, formatter8.formatDms(value2Format));
   }

   @Test
   public void formatIntegerPartis3Complex() {
      value2Format = 113.90786111111;
      assertEquals("113" + DEGREESIGN + "54" + MINUTESIGN + "28" + SECONDSIGN, formatter3.formatDms(value2Format));
   }

   @Test
   public void formatIntegerPartis3CorrectRounding() {
      value2Format = 1.01666388889;
      assertEquals("001" + DEGREESIGN + "00" + MINUTESIGN + "59" + SECONDSIGN, formatter3.formatDms(value2Format));
   }

   @Test
   public void formatDm() {
      assertEquals("01" + DEGREESIGN + "30" + MINUTESIGN, formatter2.formatDm(value2Format));
   }
}