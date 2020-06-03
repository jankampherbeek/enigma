/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.ui.shared.StyleDictionary.STYLESHEET;

/**
 * Creates a VBox, based on the Builder pattern.
 */
public class VBoxBuilder {

   private double prefWidth;
   private double padding;
   private double prefHeight;
   private Node[] children;


   public VBoxBuilder setWidth(final double prefWidth) {
      checkArgument(prefWidth > 0.0);
      this.prefWidth = prefWidth;
      return this;
   }

   public VBoxBuilder setHeight(final double prefHeight) {
      checkArgument(prefHeight > 0.0);
      this.prefHeight = prefHeight;
      return this;
   }

   public VBoxBuilder setPadding(final double padding) {
      checkArgument(padding > 0.0);
      this.padding = padding;
      return this;
   }

   public VBoxBuilder setChildren(final Node... children) {
      this.children = checkNotNull(children);
      return this;
   }

   public VBox build() {
      VBox vBox = new VBox();
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(prefWidth);
      if (prefHeight > 0.0) vBox.setPrefHeight(prefHeight);
      if (padding > 0.0) vBox.setPadding(new Insets(padding, padding, padding, padding));
      if (children != null && children.length > 0) vBox.getChildren().addAll(children);
      return vBox;
   }

}
