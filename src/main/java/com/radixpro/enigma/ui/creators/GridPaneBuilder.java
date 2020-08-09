/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Creates a GridPane, based on the Builder pattern.
 */
public class GridPaneBuilder {

   private double prefHeight;
   private double prefWidth;
   private double padding;
   private double hGap;
   private double vGap;

   public GridPaneBuilder setPrefHeight(final double prefHeight) {
      checkArgument(prefHeight > 0.0);
      this.prefHeight = prefHeight;
      return this;
   }

   public GridPaneBuilder setPrefWidth(final double prefWidth) {
      checkArgument(prefWidth > 0.0);
      this.prefWidth = prefWidth;
      return this;
   }

   public GridPaneBuilder setPadding(final double padding) {
      checkArgument(padding >= 0.0);
      this.padding = padding;
      return this;
   }

   public GridPaneBuilder setVGap(final double vGap) {
      checkArgument(vGap >= 0.0);
      this.vGap = vGap;
      return this;
   }

   public GridPaneBuilder setHGap(final double hGap) {
      checkArgument(hGap >= 0.0);
      this.hGap = hGap;
      return this;
   }

   public GridPane build() {
      GridPane gridPane = new GridPane();
      if (prefHeight > 0.0) gridPane.setPrefHeight(prefHeight);
      if (prefWidth > 0.0) gridPane.setPrefWidth(prefWidth);
      if (padding > 0.0) gridPane.setPadding(new Insets(padding, padding, padding, padding));
      if (hGap > 0.0) gridPane.setHgap(hGap);
      if (vGap > 0.0) gridPane.setVgap(vGap);
      return gridPane;
   }

}
