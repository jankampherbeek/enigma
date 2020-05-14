/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel.valuetypes;

/**
 * Creates a formatted string for a double value.
 * The string represents a decimal value and uses two positions for the integer part, if necessaryn prefixed with spaces.
 * The fraction contains always 8 positions.
 */
public class DecimalValue extends AbstractValueType {

   public DecimalValue(final double value) {
      super(value);
      performFormatting();
   }

   @Override
   protected void performFormatting() {
      double absValue = Math.abs(value);
      int degHour = (int) absValue;
      int fraction = (int) ((absValue - degHour) * 100000000);
      String content = "%2d" + "." + "%08d";
      String tempResult = String.format(content, degHour, fraction);
      String sign = (value >= 0.0 ? " " : "-");
      if (tempResult.startsWith(" ")) {
         formattedPosition = " " + sign + tempResult.substring(1);
      } else {
         formattedPosition = sign + tempResult;
      }
   }

}
