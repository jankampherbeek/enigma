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

public class StatsRangeTypesTest {

   @Test
   public void getRangeTypeForId() throws UnknownIdException {
      assertEquals(StatsRangeTypes.HOUSES, StatsRangeTypes.getRangeTypeForId(2));
   }

   @Test(expected = UnknownIdException.class)
   public void getRangeTypeForIdError() throws UnknownIdException {
      StatsRangeTypes.getRangeTypeForId(100);
   }

}