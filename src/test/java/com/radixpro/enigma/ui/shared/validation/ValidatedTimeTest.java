/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatedTimeTest {

   private final String timeTextOk = "11:05:48";
   private final String timeTextSingleDigits = "1:2:3";
   private final String timeTextHour2Large = "25:45:38";
   private final String timeTextHour2Small = "-1:45:38";
   private final String timeTextMinute2Large = "11:65:48";
   private final String timeTextMinute2Small = "11:-1:48";
   private final String timeTextSecond2Large = "11:45:60";
   private final String timeTextSecond2Small = "11:45:-3";
   private final String timeTextNoSecond = "11:45";
   private final String timeText2ManyArgs = "11:45:44:19";
   private final String timeTextNotNumeric = "11:ab:48";
   private ValidatedTime time;

   @Test
   public void happyFlow() {
      time = new ValidatedTime(timeTextOk);
      assertTrue(time.isValidated());
      assertEquals(11, time.getSimpleTime().getHour());
      assertEquals(5, time.getSimpleTime().getMinute());
      assertEquals(48, time.getSimpleTime().getSecond());
   }

   @Test
   public void singleDigits() {
      time = new ValidatedTime(timeTextSingleDigits);
      assertTrue(time.isValidated());
      assertEquals(1, time.getSimpleTime().getHour());
      assertEquals(2, time.getSimpleTime().getMinute());
      assertEquals(3, time.getSimpleTime().getSecond());
   }

   @Test
   public void happyFlowNoSecond() {
      time = new ValidatedTime(timeTextNoSecond);
      assertTrue(time.isValidated());
      assertEquals(11, time.getSimpleTime().getHour());
      assertEquals(45, time.getSimpleTime().getMinute());
      assertEquals(0, time.getSimpleTime().getSecond());
   }

   @Test
   public void hour2Large() {
      assertFalse(new ValidatedTime(timeTextHour2Large).isValidated());
   }

   @Test
   public void hour2Small() {
      assertFalse(new ValidatedTime(timeTextHour2Small).isValidated());
   }

   @Test
   public void minute2Large() {
      assertFalse(new ValidatedTime(timeTextMinute2Large).isValidated());
   }

   @Test
   public void minute2Small() {
      assertFalse(new ValidatedTime(timeTextMinute2Small).isValidated());
   }

   @Test
   public void second2Large() {
      assertFalse(new ValidatedTime(timeTextSecond2Large).isValidated());
   }

   @Test
   public void second2Small() {
      assertFalse(new ValidatedTime(timeTextSecond2Small).isValidated());
   }

   @Test
   public void tooManyArgs() {
      assertFalse(new ValidatedTime(timeText2ManyArgs).isValidated());
   }

   @Test
   public void notNumeric() {
      time = new ValidatedTime(timeTextNotNumeric);
      assertFalse(time.isValidated());
      assertEquals(0, time.getSimpleTime().getHour());
      assertEquals(0, time.getSimpleTime().getMinute());
      assertEquals(0, time.getSimpleTime().getSecond());
   }


}
