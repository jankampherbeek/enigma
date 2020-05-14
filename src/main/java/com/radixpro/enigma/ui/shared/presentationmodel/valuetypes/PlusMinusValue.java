/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel.valuetypes;

/**
 * Creates a formatted string for a double value and expliciat indication of negative or positive.
 * The string represents a decimal value and uses two positions for the integer part, prefixed with a '+' or a '-' and
 * if necessary prefixed with spaces.
 * The fraction contains always 8 positions.
 */
public class PlusMinusValue extends AbstractSexagValue {

   public PlusMinusValue(double value) {
      super(value);
      lengthOfIntegerPart = 2;
      performFormatting();
   }

   @Override
   protected void performFormatting() {
      String tempPosition = performSexagFormatting(value);
      formattedPosition = addPlusMinus(tempPosition);
   }

   private String addPlusMinus(final String tempPosition) {
      final String sign = (value >= 0.0 ? "+" : "-");
      String tempResult;
      if (tempPosition.startsWith("  ")) {
         tempResult = "  " + sign + tempPosition.substring(2);
      } else if (tempPosition.startsWith(" ")) {
         tempResult = " " + sign + tempPosition.substring(1);
      } else {
         tempResult = sign + tempPosition;
      }
      return tempResult;
   }
}
