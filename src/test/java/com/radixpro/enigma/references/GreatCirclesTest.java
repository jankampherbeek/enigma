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

public class GreatCirclesTest {

   @Test
   public void getGreatCircleForId() throws UnknownIdException {
      assertEquals(GreatCircles.HORIZON, GreatCircles.getGreatCircleForId(3));
   }

   @Test(expected = UnknownIdException.class)
   public void getGreatCircleForIdError() throws UnknownIdException {
      GreatCircles.getGreatCircleForId(100);
   }

}