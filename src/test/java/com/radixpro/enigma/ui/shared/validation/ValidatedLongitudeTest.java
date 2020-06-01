/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatedLongitudeTest {

   private static final double DELTA = 0.00000001;
   private final String longTextOk = "152:09:20";
   private final String longTextSingleDigit = "4:3:2";
   private final String longTextDegree2Large = "191:09:20";
   private final String longTextDegree2Small = "-191:09:20";
   private final String longTextMinute2Large = "152:66:20";
   private final String longTextMinute2Small = "152:-60:20";
   private final String longTextSecond2Large = "152:09:60";
   private final String longTextSecond2Small = "152:09:-61";
   private final String longText180DegreesOk = "180:00:00";
   private final String longText180DegreesError = "180:00:01";
   private final String longText180DegreesMinusOk = "-180:00:00";
   private final String longText180DegreesMinusError = "-180:00:01";
   private final String longTextNoSecond = "52:09";
   private final String longTextNotNumeric = "52:ab:20";
   private final String longText2ManyArgs = "52:09:20:33";
   private ValidatedLongitude valLong;

   @Test
   public void happyFlow() {
      valLong = new ValidatedLongitude(longTextOk);
      assertTrue(valLong.isValidated());
      assertEquals(152.155555555555556, valLong.getValue(), DELTA);
   }

   @Test
   public void singleDigit() {
      valLong = new ValidatedLongitude(longTextSingleDigit);
      assertTrue(valLong.isValidated());
      assertEquals(4.05055555555556, valLong.getValue(), DELTA);
   }

   @Test
   public void degree2Large() {
      valLong = new ValidatedLongitude(longTextDegree2Large);
      assertFalse(valLong.isValidated());
      assertEquals(0.0, valLong.getValue(), DELTA);
   }

   @Test
   public void degree2Small() {
      valLong = new ValidatedLongitude(longTextDegree2Small);
      assertFalse(valLong.isValidated());
      assertEquals(0.0, valLong.getValue(), DELTA);
   }

   @Test
   public void minute2Large() {
      assertFalse(new ValidatedLongitude(longTextMinute2Large).isValidated());
   }

   @Test
   public void minute2Small() {
      assertFalse(new ValidatedLongitude(longTextMinute2Small).isValidated());
   }

   @Test
   public void second2Large() {
      assertFalse(new ValidatedLongitude(longTextSecond2Large).isValidated());
   }

   @Test
   public void second2Small() {
      assertFalse(new ValidatedLongitude(longTextSecond2Small).isValidated());
   }

   @Test
   public void correct180DegreesPlus() {
      valLong = new ValidatedLongitude(longText180DegreesOk);
      assertTrue(valLong.isValidated());
      assertEquals(180.0, valLong.getValue(), DELTA);
   }

   @Test
   public void correct180DegreesMinus() {
      valLong = new ValidatedLongitude(longText180DegreesMinusOk);
      assertTrue(valLong.isValidated());
      assertEquals(-180.0, valLong.getValue(), DELTA);
   }

   @Test
   public void error180DegreesPlus() {
      valLong = new ValidatedLongitude(longText180DegreesError);
      assertFalse(valLong.isValidated());
      assertEquals(0.0, valLong.getValue(), DELTA);
   }

   @Test
   public void error180DegreesMinus() {
      valLong = new ValidatedLongitude(longText180DegreesMinusError);
      assertFalse(valLong.isValidated());
      assertEquals(0.0, valLong.getValue(), DELTA);
   }

   @Test
   public void noSecond() {
      valLong = new ValidatedLongitude(longTextNoSecond);
      assertTrue(valLong.isValidated());
      assertEquals(52.15, valLong.getValue(), DELTA);
   }

   @Test
   public void notNumeric() {
      valLong = new ValidatedLongitude(longTextNotNumeric);
      assertFalse(valLong.isValidated());
      assertEquals(0.0, valLong.getValue(), DELTA);
   }

   @Test
   public void tooManyArgs() {
      valLong = new ValidatedLongitude(longText2ManyArgs);
      assertFalse(valLong.isValidated());
      assertEquals(0.0, valLong.getValue(), DELTA);
   }

   @Test
   public void getDegrees() {
      valLong = new ValidatedLongitude((longTextOk));
      assertEquals(152, valLong.getDegrees());
      //   private final String longTextOk = "152:09:20";
   }

   @Test
   public void getMinutes() {
      valLong = new ValidatedLongitude((longTextOk));
      assertEquals(9, valLong.getMinutes());
   }

   @Test
   public void getSeconds() {
      valLong = new ValidatedLongitude((longTextOk));
      assertEquals(20, valLong.getSeconds());
   }

   @Test
   public void applyWesternLongitude() {
      valLong = new ValidatedLongitude(longTextOk);
      valLong.applyWesternLongitude();
      assertTrue(valLong.isValidated());
      assertEquals(-152.155555555555556, valLong.getValue(), DELTA);
   }
}