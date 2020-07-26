/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.util;

import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static java.lang.Double.NaN;
import static org.junit.Assert.assertEquals;

public class EnigmaMathTest {

   @Test
   public void sin() {
      assertEquals(0.207911690817759, EnigmaMath.sin(12), DELTA_8_POS);
   }

   @Test
   public void sinNeg() {
      assertEquals(-0.207911690817759, EnigmaMath.sin(-12), DELTA_8_POS);
   }

   @Test
   public void sinZero() {
      assertEquals(0, EnigmaMath.sin(0), DELTA_8_POS);
   }

   @Test
   public void sin90() {
      assertEquals(1, EnigmaMath.sin(90), DELTA_8_POS);
   }

   @Test
   public void cos() {
      assertEquals(0.96126169593831886, EnigmaMath.cos(16), DELTA_8_POS);
   }

   @Test
   public void cosNeg() {
      assertEquals(0.96126169593831886, EnigmaMath.cos(-16), DELTA_8_POS);
   }

   @Test
   public void cosZero() {
      assertEquals(1, EnigmaMath.cos(0), DELTA_8_POS);
   }

   @Test
   public void cos90() {
      assertEquals(0, EnigmaMath.cos(90), DELTA_8_POS);
   }

   @Test
   public void tan() {
      assertEquals(0.08748866352592, EnigmaMath.tan(5), DELTA_8_POS);
   }

   @Test
   public void tanNeg() {
      assertEquals(-0.08748866352592, EnigmaMath.tan(-5), DELTA_8_POS);
   }

   @Test
   public void tanZero() {
      assertEquals(0, EnigmaMath.tan(0), DELTA_8_POS);
   }

   @Test
   public void asin() {
      assertEquals(12, EnigmaMath.asin(0.207911690817759), DELTA_8_POS);
   }

   @Test
   public void asinNeg() {
      assertEquals(-12, EnigmaMath.asin(-0.207911690817759), DELTA_8_POS);
   }

   @Test
   public void asinZero() {
      assertEquals(0, EnigmaMath.asin(0), DELTA_8_POS);
   }

   @Test
   public void asin90() {
      assertEquals(90, EnigmaMath.asin(1), DELTA_8_POS);
   }

   @Test
   public void acos() {
      assertEquals(16, EnigmaMath.acos(0.96126169593831886), DELTA_8_POS);
   }

   @Test
   public void acosNeg() {
      assertEquals(164, EnigmaMath.acos(-0.96126169593831886), DELTA_8_POS);
   }

   @Test
   public void acosZero() {
      assertEquals(90, EnigmaMath.acos(0), DELTA_8_POS);
   }

   @Test
   public void acos90() {
      assertEquals(NaN, EnigmaMath.acos(90), DELTA_8_POS);
   }

   @Test
   public void atan() {
      assertEquals(5, EnigmaMath.atan(0.08748866352592), DELTA_8_POS);
   }

   @Test
   public void atanNeg() {
      assertEquals(-5, EnigmaMath.atan(-0.08748866352592), DELTA_8_POS);
   }

   @Test
   public void atanZero() {
      assertEquals(0, EnigmaMath.atan(0), DELTA_8_POS);
   }

   @Test
   public void atan2() {
      assertEquals(EnigmaMath.atan(2.5), EnigmaMath.atan2(5, 2), DELTA_8_POS);
   }

}