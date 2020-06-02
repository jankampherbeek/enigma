/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import javafx.scene.control.Label;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a Label, based on the Builder pattern.
 */
public class LabelBuilder {

   private final String text;
   private double prefWidth;
   private double layoutX;
   private double layoutY;
   private String styleClass;

   public LabelBuilder(final String text) {
      this.text = checkNotNull(text);
   }

   public LabelBuilder setPrefWidth(final double prefWidth) {
      this.prefWidth = prefWidth;
      return this;
   }

   public LabelBuilder setLayoutX(final double layoutX) {
      this.layoutX = layoutX;
      return this;
   }

   public LabelBuilder setLayoutY(final double layoutY) {
      this.layoutY = layoutY;
      return this;
   }

   public LabelBuilder setStyleClass(final String styleClass) {
      this.styleClass = checkNotNull(styleClass);
      return this;
   }

   public Label build() {
      Label label = new Label(text);
      if (prefWidth > 0.0) label.setPrefWidth(prefWidth);
      if (layoutX > 0.0) label.setLayoutX(layoutX);
      if (layoutY > 0.0) label.setLayoutY(layoutY);
      if (styleClass != null && !styleClass.isEmpty()) label.getStyleClass().add(styleClass);
      return label;
   }

}
