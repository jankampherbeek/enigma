/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

/**
 * Factory for GridPanes.
 */
public class GridPaneFactory {   // TODO remove and use Builder

   private GridPaneFactory() {
      // prevent instantiation
   }

   public static GridPane createGridPane(final double height, final double width, final double gap) {
      GridPane gridPane = new GridPane();
      gridPane.setPrefHeight(height);
      gridPane.setPrefWidth(width);
      gridPane.setHgap(gap);
      gridPane.setVgap(gap);
      gridPane.setPadding(new Insets(gap, gap, gap, gap));
      return gridPane;
   }
}
