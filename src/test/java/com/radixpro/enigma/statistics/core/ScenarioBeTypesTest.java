/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.core;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScenarioBeTypesTest {

   @Test
   public void getScenarioTypeForId() throws UnknownIdException {
      assertEquals(ScenarioTypes.COUNTS, ScenarioTypes.getScenarioTypeForId(3));
   }

   @Test(expected = UnknownIdException.class)
   public void getScenarioTypeForIdError() throws UnknownIdException {
      ScenarioTypes.getScenarioTypeForId(500);
   }

}