/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Creates a ChoiceBox, based on the Builder pattern.
 */
public class ChoiceBoxBuilder {

   private double prefHeight;
   private double prefWidth;
   private String styleClass;
   private ObservableList<String> items;

   public ChoiceBoxBuilder setPrefHeight(final double prefHeight) {
      checkArgument(prefHeight > 0.0);
      this.prefHeight = prefHeight;
      return this;
   }

   public ChoiceBoxBuilder setPrefWidth(final double prefWidth) {
      checkArgument(prefWidth > 0.0);
      this.prefWidth = prefWidth;
      return this;
   }

   public ChoiceBoxBuilder setStyleClass(final String styleClass) {
      checkArgument(null != styleClass && !styleClass.isEmpty());
      this.styleClass = styleClass;
      return this;
   }

   public ChoiceBoxBuilder setItems(final ObservableList<String> items) {
      checkArgument(null != items && !items.isEmpty());
      this.items = items;
      return this;
   }

   public ChoiceBox build() {
      ChoiceBox choiceBox = new ChoiceBox();
      if (prefWidth > 0.0) choiceBox.setPrefWidth(prefWidth);
      if (prefHeight > 0.0) choiceBox.setPrefHeight(prefHeight);
      if (null != styleClass && !styleClass.isEmpty()) choiceBox.getStyleClass().add(styleClass);
      if (null != items && !items.isEmpty()) choiceBox.setItems(items);
      return choiceBox;
   }

}
