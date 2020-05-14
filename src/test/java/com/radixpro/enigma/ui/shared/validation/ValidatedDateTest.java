/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatedDateTest {

   private final String dateTextOk = "1953/01/29/g";
   private final String dateText2FewArgs = "1953/01/29";
   private final String dateTextSingleDigit = "3/5/8/j";
   private final String dateTextMonth2Large = "2000/14/1/g";
   private final String dateTextMonth2Small = "2000/-1/1/g";
   private final String dateTextDay2Large = "2000/1/32/g";
   private final String dateTextDay2Small = "2000/1/-3/g";
   private final String dateTextNotNumeric = "2000/ab/03/g";

   @Test
   public void happyFlow() {
      var valDate = new ValidatedDate(dateTextOk);
      assertTrue(valDate.isValidated());
      assertEquals(1953, valDate.getSimpleDate().getYear());
      assertEquals(1, valDate.getSimpleDate().getMonth());
      assertEquals(29, valDate.getSimpleDate().getDay());
   }

   @Test
   public void happyFlowSingleDigit() {
      var valDate = new ValidatedDate(dateTextSingleDigit);
      assertTrue(valDate.isValidated());
      assertEquals(3, valDate.getSimpleDate().getYear());
      assertEquals(5, valDate.getSimpleDate().getMonth());
      assertEquals(8, valDate.getSimpleDate().getDay());
   }

   @Test
   public void tooFewArgs() {
      var valDate = new ValidatedDate(dateText2FewArgs);
      assertFalse(valDate.isValidated());
      assertEquals(0, valDate.getSimpleDate().getYear());
      assertEquals(1, valDate.getSimpleDate().getMonth());
      assertEquals(1, valDate.getSimpleDate().getDay());
   }

   @Test
   public void month2Large() {
      assertFalse(new ValidatedDate(dateTextMonth2Large).isValidated());
   }

   @Test
   public void month2Small() {
      assertFalse(new ValidatedDate(dateTextMonth2Small).isValidated());
   }

   @Test
   public void day2Large() {
      assertFalse(new ValidatedDate(dateTextDay2Large).isValidated());
   }

   @Test
   public void day2Small() {
      assertFalse(new ValidatedDate(dateTextDay2Small).isValidated());
   }

   @Test
   public void notNumeric() {
      assertFalse(new ValidatedDate(dateTextNotNumeric).isValidated());
   }

}