/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.creators;

import javafx.scene.control.TextField;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Factory for TextFields
 */
public class TextFieldFactory {

   private TextFieldFactory() {
      // prevent instantiation
   }

   public static TextField createTextField(final double height, final double width, final String styleClass) {
      checkNotNull(styleClass);
      TextField textField = new TextField();
      textField.setPrefHeight(height);
      textField.setPrefWidth(width);
      textField.getStyleClass().add(styleClass);
      return textField;
   }
}
