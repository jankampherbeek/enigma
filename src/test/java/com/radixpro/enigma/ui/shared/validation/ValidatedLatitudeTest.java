/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatedLatitudeTest {

   private static final double DELTA = 0.00000001;
   private final String latTextOk = "52:09:20";
   private final String latTextSingleDigit = "4:3:2";
   private final String latTextDegree2Large = "91:09:20";
   private final String latTextDegree2Small = "-91:09:20";
   private final String latTextMinute2Large = "52:66:20";
   private final String latTextMinute2Small = "52:-60:20";
   private final String latTextSecond2Large = "52:09:60";
   private final String latTextSecond2Small = "52:09:-61";
   private final String latTextNoSecond = "52:09";
   private final String latTextNotNumeric = "52:ab:20";
   private final String latText2ManyArgs = "52:09:20:33";
   private ValidatedLatitude valLat;

   @Test
   public void happyFlow() {
      valLat = new ValidatedLatitude(latTextOk);
      assertTrue(valLat.isValidated());
      assertEquals(52.15555555555556, valLat.getValue(), DELTA);
   }

   @Test
   public void singleDigit() {
      valLat = new ValidatedLatitude(latTextSingleDigit);
      assertTrue(valLat.isValidated());
      assertEquals(4.05055555555556, valLat.getValue(), DELTA);
   }

   @Test
   public void noSecond() {
      valLat = new ValidatedLatitude(latTextNoSecond);
      assertTrue(valLat.isValidated());
      assertEquals(52.15, valLat.getValue(), DELTA);
   }


   @Test
   public void degree2Large() {
      valLat = new ValidatedLatitude(latTextDegree2Large);
      assertFalse(valLat.isValidated());
      assertEquals(0.0, valLat.getValue(), DELTA);
   }

   @Test
   public void degree2Small() {
      assertFalse(new ValidatedLatitude(latTextDegree2Small).isValidated());
   }

   @Test
   public void minute2Large() {
      assertFalse(new ValidatedLatitude(latTextMinute2Large).isValidated());
   }

   @Test
   public void minute2Small() {
      assertFalse(new ValidatedLatitude(latTextMinute2Small).isValidated());
   }

   @Test
   public void second2Large() {
      assertFalse(new ValidatedLatitude(latTextSecond2Large).isValidated());
   }

   @Test
   public void second2Small() {
      assertFalse(new ValidatedLatitude(latTextSecond2Small).isValidated());
   }

   @Test
   public void notNumeric() {
      assertFalse(new ValidatedLatitude(latTextNotNumeric).isValidated());
   }

   @Test
   public void tooManyArgs() {
      assertFalse(new ValidatedLatitude(latText2ManyArgs).isValidated());
   }

   @Test
   public void getDegrees() {
      valLat = new ValidatedLatitude(latTextOk);
      assertEquals(52, valLat.getDegrees());
   }

   @Test
   public void getMinutes() {
      valLat = new ValidatedLatitude(latTextOk);
      assertEquals(9, valLat.getMinutes());
   }

   @Test
   public void getSeconds() {
      valLat = new ValidatedLatitude(latTextOk);
      assertEquals(20, valLat.getSeconds());
   }

}