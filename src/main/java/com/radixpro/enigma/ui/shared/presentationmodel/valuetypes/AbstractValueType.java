/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel.valuetypes;

/**
 * Parent for classes that construct a formatted value.
 */
public abstract class AbstractValueType {

   protected final double value;

   protected String formattedPosition;

   public AbstractValueType(final double value) {
      this.value = value;
   }

   protected abstract void performFormatting();

   public double getValue() {
      return value;
   }

   public String getFormattedPosition() {
      return formattedPosition;
   }
}
