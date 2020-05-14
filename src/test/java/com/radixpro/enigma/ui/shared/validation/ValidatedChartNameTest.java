/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatedChartNameTest {

   private static final String textCorrectName = "Vettius Valens";
   private static final String textEmptyName = "";
   private ValidatedChartName valChartName;

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void happyFlow() {
      valChartName = new ValidatedChartName(textCorrectName);
      assertTrue(valChartName.isValidated());
      assertEquals(textCorrectName, valChartName.getNameText());
   }

   @Test
   public void emptyName() {
      valChartName = new ValidatedChartName(textEmptyName);
      assertFalse(valChartName.isValidated());
      assertEquals(textEmptyName, valChartName.getNameText());
   }

}