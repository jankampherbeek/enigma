/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.ui.validators.UiValidatorsInjector;
import com.radixpro.enigma.ui.validators.ValidatedChartName;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatedChartNameTest {

   private static final String textCorrectName = "Vettius Valens";
   private static final String textEmptyName = "";
   private ValidatedChartName valChartName;

   @Before
   public void setUp() {
      valChartName = UiValidatorsInjector.injectValidatedChartName(new AppScope());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void happyFlow() {
      assertTrue(valChartName.validate(textCorrectName));
   }

   @Test
   public void emptyName() {
      assertFalse(valChartName.validate(textEmptyName));
   }

}