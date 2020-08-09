/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.scene.layout.Pane;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for Panes.
 */
public class PaneFactory {
   // TODO replace with PaneBuilder

   private PaneFactory() {
      // prevent instantiation
   }

   public static Pane createPane(final double height, final double width, final String styleClass) {
      checkNotNull(styleClass);
      Pane pane = createPane(height, width);
      pane.getStyleClass().add(styleClass);
      return pane;
   }

   public static Pane createPane(final double height, final double width) {
      Pane pane = new Pane();
      pane.setPrefWidth(width);
      pane.setPrefHeight(height);
      return pane;
   }

}
