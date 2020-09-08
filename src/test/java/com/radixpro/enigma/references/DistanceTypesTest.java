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

public class DistanceTypesTest {

   @Test
   public void getDistanceTypeForId() throws UnknownIdException {
      assertEquals(DistanceTypes.LATITUDE, DistanceTypes.getDistanceTypeForId(2));
   }

   @Test(expected = UnknownIdException.class)
   public void getDistanceTypeForIdError() throws UnknownIdException {
      DistanceTypes.getDistanceTypeForId(100);
   }

}