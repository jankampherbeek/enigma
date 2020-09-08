/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatsScenarioTypesTest {

   @Test
   public void getScenarioTypeForId() throws UnknownIdException {
      assertEquals(StatsScenarioTypes.MULTIPLE_SINGLE, StatsScenarioTypes.getScenarioTypeForId(3));
   }

   @Test(expected = UnknownIdException.class)
   public void getScenarioTypeForIdError() throws UnknownIdException {
      StatsScenarioTypes.getScenarioTypeForId(500);
   }

}