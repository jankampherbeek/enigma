/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.scene.control.TextField;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Creates a TextField, based on the Builder pattern.
 */
public class TextFieldBuilder {

   private double prefHeight;
   private double prefWidth;
   private String styleClass;
   private String text;

   public TextFieldBuilder setPrefHeight(final double prefHeight) {
      checkArgument(prefHeight > 0.0);
      this.prefHeight = prefHeight;
      return this;
   }

   public TextFieldBuilder setPrefWidth(final double prefWidth) {
      checkArgument(prefWidth > 0.0);
      this.prefWidth = prefWidth;
      return this;
   }

   public TextFieldBuilder setStyleClass(final String styleClass) {
      checkArgument(null != styleClass && !styleClass.isEmpty());
      this.styleClass = styleClass;
      return this;
   }

   public TextFieldBuilder setText(final String text) {
      checkArgument(null != text && !text.isEmpty());
      this.text = text;
      return this;
   }

   public TextField build() {
      TextField textField = new TextField();
      if (prefHeight > 0.0) textField.setPrefHeight(prefHeight);
      if (prefWidth > 0.0) textField.setPrefWidth(prefWidth);
      if (null != styleClass && !styleClass.isEmpty()) textField.getStyleClass().add(styleClass);
      if (null != text && !text.isEmpty()) textField.setText(text);
      return textField;
   }

}
