/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a CheckBox, based on the Builder pattern.
 */
public class CheckBoxBuilder {

   private Pos alignment;
   private String styleClass;

   public CheckBoxBuilder setAlignment(final Pos alignment) {
      this.alignment = checkNotNull(alignment);
      return this;
   }

   public CheckBoxBuilder setStyleClass(final String styleClass) {
      checkArgument(null != styleClass && !styleClass.isBlank());
      this.styleClass = styleClass;
      return this;
   }

   public CheckBox build() {
      CheckBox checkBox = new CheckBox();
      if (null != alignment) checkBox.setAlignment(alignment);
      if (null != styleClass && !styleClass.isBlank()) checkBox.getStyleClass().add(styleClass);
      return checkBox;
   }

}
