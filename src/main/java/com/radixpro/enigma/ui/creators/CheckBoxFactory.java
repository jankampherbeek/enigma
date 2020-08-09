/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for CheckBoxes
 */
public class CheckBoxFactory {

   private CheckBoxFactory() {
      // prevent instantiation
   }

   public static CheckBox createCheckBox(final Pos alignment, final String styleClass) {
      checkNotNull(alignment);
      checkNotNull(styleClass);
      CheckBox checkBox = new CheckBox();
      checkBox.getStyleClass().add(styleClass);
      checkBox.setAlignment(alignment);
      return checkBox;
   }
}
