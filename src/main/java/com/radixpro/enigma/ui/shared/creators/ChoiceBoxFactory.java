/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.creators;

import javafx.scene.control.ChoiceBox;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for ChoiceBoxes.
 */
@SuppressWarnings("rawtypes")
public class ChoiceBoxFactory {

   private ChoiceBoxFactory() {
      // prevent instantiation.
   }

   public static ChoiceBox createChoiceBox(final double height, final double width, final String styleClass) {
      checkNotNull(styleClass);
      ChoiceBox choiceBox = new ChoiceBox();
      choiceBox.setPrefHeight(height);
      choiceBox.setPrefWidth(width);
      choiceBox.getStyleClass().add(styleClass);
      return choiceBox;
   }
}
