/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel.valuetypes;

import org.junit.Test;

import static com.radixpro.enigma.shared.EnigmaDictionary.*;
import static org.junit.Assert.assertEquals;

public class PlusMinusValueTest {

   private double posValue = 12.5;
   private double negValue = -8.25;
   private double smallValue = 0.5;
   private String expectedPositive = "+12" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN;
   private String expectedNegative = " -8" + DEGREESIGN + "15" + MINUTESIGN + "00" + SECONDSIGN;
   private String expectedSmall = " +0" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN;

   @Test
   public void getFormattedPositionPositive() {
      PlusMinusValue plusMinusValue = new PlusMinusValue(posValue);
      assertEquals(expectedPositive, plusMinusValue.getFormattedPosition());
   }

   @Test
   public void getFormattedPositionNegative() {
      PlusMinusValue plusMinusValue = new PlusMinusValue(negValue);
      assertEquals(expectedNegative, plusMinusValue.getFormattedPosition());
   }

   @Test
   public void getFormattedPositionSmall() {
      PlusMinusValue plusMinusValue = new PlusMinusValue(smallValue);
      assertEquals(expectedSmall, plusMinusValue.getFormattedPosition());
   }

}