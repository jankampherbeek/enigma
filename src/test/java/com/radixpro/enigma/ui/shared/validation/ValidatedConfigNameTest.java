/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import com.radixpro.enigma.ui.validators.ValidatedConfigName;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatedConfigNameTest {

   private ValidatedConfigName valConfigName;


   @Test
   public void happyFlow() {
      String correctName = "My Great Config";
      valConfigName = new ValidatedConfigName(correctName);
      assertTrue(valConfigName.isValidated());
      assertEquals(correctName, valConfigName.getNameText());
   }

   @Test
   public void emptyName() {
      String emptyName = "";
      valConfigName = new ValidatedConfigName(emptyName);
      assertFalse(valConfigName.isValidated());
      assertEquals(emptyName, valConfigName.getNameText());
   }

}

