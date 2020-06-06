/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatedTimeTest {

   private ValidatedTime time;

   @Test
   public void happyFlow() {
      String timeTextOk = "11:05:48";
      time = new ValidatedTime(timeTextOk);
      assertTrue(time.isValidated());
      assertEquals(11, time.getSimpleTime().getHour());
      assertEquals(5, time.getSimpleTime().getMinute());
      assertEquals(48, time.getSimpleTime().getSecond());
   }

   @Test
   public void singleDigits() {
      String timeTextSingleDigits = "1:2:3";
      time = new ValidatedTime(timeTextSingleDigits);
      assertTrue(time.isValidated());
      assertEquals(1, time.getSimpleTime().getHour());
      assertEquals(2, time.getSimpleTime().getMinute());
      assertEquals(3, time.getSimpleTime().getSecond());
   }

   @Test
   public void happyFlowNoSecond() {
      String timeTextNoSecond = "11:45";
      time = new ValidatedTime(timeTextNoSecond);
      assertTrue(time.isValidated());
      assertEquals(11, time.getSimpleTime().getHour());
      assertEquals(45, time.getSimpleTime().getMinute());
      assertEquals(0, time.getSimpleTime().getSecond());
   }

   @Test
   public void hour2Large() {
      String timeTextHour2Large = "25:45:38";
      assertFalse(new ValidatedTime(timeTextHour2Large).isValidated());
   }

   @Test
   public void hour2Small() {
      String timeTextHour2Small = "-1:45:38";
      assertFalse(new ValidatedTime(timeTextHour2Small).isValidated());
   }

   @Test
   public void minute2Large() {
      String timeTextMinute2Large = "11:65:48";
      assertFalse(new ValidatedTime(timeTextMinute2Large).isValidated());
   }

   @Test
   public void minute2Small() {
      String timeTextMinute2Small = "11:-1:48";
      assertFalse(new ValidatedTime(timeTextMinute2Small).isValidated());
   }

   @Test
   public void second2Large() {
      String timeTextSecond2Large = "11:45:60";
      assertFalse(new ValidatedTime(timeTextSecond2Large).isValidated());
   }

   @Test
   public void second2Small() {
      String timeTextSecond2Small = "11:45:-3";
      assertFalse(new ValidatedTime(timeTextSecond2Small).isValidated());
   }

   @Test
   public void tooManyArgs() {
      String timeText2ManyArgs = "11:45:44:19";
      assertFalse(new ValidatedTime(timeText2ManyArgs).isValidated());
   }

   @Test
   public void notNumeric() {
      String timeTextNotNumeric = "11:ab:48";
      time = new ValidatedTime(timeTextNotNumeric);
      assertFalse(time.isValidated());
      assertEquals(0, time.getSimpleTime().getHour());
      assertEquals(0, time.getSimpleTime().getMinute());
      assertEquals(0, time.getSimpleTime().getSecond());
   }


}
