/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.creators;

import javafx.scene.control.Label;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for buttons.
 */
public class LabelFactory {
   // TODO replace with LabelBuilder
   private LabelFactory() {
      // prevent instantiation
   }

   public static Label createLabel(final String text, final double layoutX, final double layoutY, final String styleClass) {
      checkNotNull(text);
      checkNotNull(styleClass);
      Label label = createLabel(text, layoutX, layoutY);
      label.getStyleClass().add(styleClass);
      return label;
   }

   public static Label createLabel(final String text, final double layoutX, final double layoutY) {
      checkNotNull(text);
      Label label = createLabel(text);
      label.setLayoutX(layoutX);
      label.setLayoutY(layoutY);
      return label;
   }


   public static Label createLabel(final String text, final String styleClass, final double width) {
      checkNotNull(text);
      checkNotNull(styleClass);
      Label label = createLabel(text, styleClass);
      label.setPrefWidth(width);
      return label;
   }

   public static Label createLabel(final String text, final double width) {
      Label label = createLabel(checkNotNull(text));
      label.setPrefWidth(width);
      return label;
   }

   public static Label createLabel(final String text, final String styleClass) {
      checkNotNull(text);
      checkNotNull(styleClass);
      Label label = createLabel(text);
      label.getStyleClass().add(styleClass);
      return label;
   }

   public static Label createLabel(final String text) {
      checkNotNull(text);
      Label label = new Label();
      label.setText(text);
      return label;
   }

}
