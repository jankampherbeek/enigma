/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel.valuetypes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DecimalValueTest {

   private DecimalValue decimalValue;

   @Test
   public void getFormattedPosition() {
      decimalValue = new DecimalValue(12.386759267);
      assertEquals(" 12.38675926", decimalValue.getFormattedPosition());
   }

   @Test
   public void getFormattedPositionWithLeadingSpace() {
      decimalValue = new DecimalValue(0.002);
      assertEquals("  0.00200000", decimalValue.getFormattedPosition());
   }

   @Test
   public void getFormattedPositionNegative() {
      decimalValue = new DecimalValue(-1.5);
      assertEquals(" -1.50000000", decimalValue.getFormattedPosition());
   }

}