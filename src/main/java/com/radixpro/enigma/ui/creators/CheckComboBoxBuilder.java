/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import org.controlsfx.control.CheckComboBox;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Creates a CheckComboBox, based on the Builder pattern.
 */
public class CheckComboBoxBuilder {

   private double prefWidth;

   public CheckComboBoxBuilder setPrefWidth(final double prefWidth) {
      checkArgument(prefWidth > 0.0);
      this.prefWidth = prefWidth;
      return this;
   }

   public CheckComboBox build() {
      CheckComboBox checkComboBox = new CheckComboBox();
      if (prefWidth > 0.0) checkComboBox.setPrefWidth(prefWidth);
      return checkComboBox;
   }

}
