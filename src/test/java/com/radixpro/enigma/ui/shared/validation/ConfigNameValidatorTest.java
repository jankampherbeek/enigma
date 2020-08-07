/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.ui.validators.ConfigNameValidator;
import com.radixpro.enigma.ui.validators.UiValidatorsInjector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConfigNameValidatorTest {

   private ConfigNameValidator validator;

   @Before
   public void setUp() {
      validator = UiValidatorsInjector.injectConfigNameValidator(new AppScope());
   }

   @Test
   public void happyFlow() {
      String correctName = "My Great Config";
      assertTrue(validator.validate(correctName));
   }

   @Test
   public void emptyName() {
      String emptyName = "";
      assertFalse(validator.validate(emptyName));
   }

}

