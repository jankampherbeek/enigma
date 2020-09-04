/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Creates a HBox, based on the Builder pattern.
 */
public class HBoxBuilder {

   private double prefWidth;
   private double prefHeight;
   private Node[] children;

   public HBoxBuilder setPrefWidth(final double prefWidth) {
      checkArgument(prefWidth > 0.0);
      this.prefWidth = prefWidth;
      return this;
   }

   public HBoxBuilder setPrefHeight(final double prefHeight) {
      checkArgument(prefHeight > 0.0);
      this.prefHeight = prefHeight;
      return this;
   }

   public HBoxBuilder setChildren(@NotNull final Node... children) {
      this.children = children;
      return this;
   }

   public HBox build() {
      HBox hBox = new HBox();
      if (prefWidth > 0.0) hBox.setPrefWidth(prefWidth);
      if (prefHeight > 0.0) hBox.setPrefHeight(prefHeight);
      if (null != children && children.length > 0) hBox.getChildren().addAll(children);
      return hBox;
   }

}
