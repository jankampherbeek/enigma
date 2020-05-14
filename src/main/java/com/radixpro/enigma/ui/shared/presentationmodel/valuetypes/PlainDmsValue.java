/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel.valuetypes;

/**
 * Creates a formatted string for a double value.
 * The string represents a decimal value and uses three positions for the integer part, if necessary prefixed with spaces.
 * The fraction contains always 8 positions.
 */
public class PlainDmsValue extends AbstractSexagValue {

   public PlainDmsValue(double value) {
      super(value);
      performFormatting();
   }

   @Override
   protected void performFormatting() {
      formattedPosition = performSexagFormatting(value);
   }
}
