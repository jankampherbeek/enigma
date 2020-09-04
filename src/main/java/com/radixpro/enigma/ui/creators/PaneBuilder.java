/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

public class PaneBuilder {

   private double width;
   private double height;
   private String styleClass;
   private Node[] children;

   public PaneBuilder setWidth(final double width) {
      checkArgument(width > 0.0);
      this.width = width;
      return this;
   }

   public PaneBuilder setHeight(final double height) {
      checkArgument(height > 0.0);
      this.height = height;
      return this;
   }

   public PaneBuilder setStyleClass(@NotNull final String styleClass) {
      this.styleClass = styleClass;
      return this;
   }

   public PaneBuilder setChildren(@NotNull final Node... children) {
      this.children = children;
      return this;
   }

   public Pane build() {
      Pane pane = new Pane();
      if (width > 0.0) pane.setPrefWidth(width);
      if (height > 0.0) pane.setPrefHeight(height);
      if (styleClass != null && !styleClass.isBlank()) pane.getStyleClass().add(styleClass);
      if (children != null && children.length > 0) pane.getChildren().addAll(children);
      return pane;
   }
}
