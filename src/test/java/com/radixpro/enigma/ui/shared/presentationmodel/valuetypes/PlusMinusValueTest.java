/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel.valuetypes;

import org.junit.Test;

import static com.radixpro.enigma.shared.common.EnigmaDictionary.*;
import static org.junit.Assert.assertEquals;

public class PlusMinusValueTest {

   @Test
   public void getFormattedPositionPositive() {
      double posValue = 12.5;
      PlusMinusValue plusMinusValue = new PlusMinusValue(posValue);
      String expectedPositive = "+12" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN;
      assertEquals(expectedPositive, plusMinusValue.getFormattedPosition());
   }

   @Test
   public void getFormattedPositionNegative() {
      double negValue = -8.25;
      PlusMinusValue plusMinusValue = new PlusMinusValue(negValue);
      String expectedNegative = " -8" + DEGREESIGN + "15" + MINUTESIGN + "00" + SECONDSIGN;
      assertEquals(expectedNegative, plusMinusValue.getFormattedPosition());
   }

   @Test
   public void getFormattedPositionSmall() {
      double smallValue = 0.5;
      PlusMinusValue plusMinusValue = new PlusMinusValue(smallValue);
      String expectedSmall = " +0" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN;
      assertEquals(expectedSmall, plusMinusValue.getFormattedPosition());
   }

}