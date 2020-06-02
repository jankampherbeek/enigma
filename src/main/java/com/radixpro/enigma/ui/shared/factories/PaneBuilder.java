/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a Pane, based on the Builder pattern.
 */
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

   public PaneBuilder setStyleClass(final String styleClass) {

      this.styleClass = checkNotNull(styleClass);
      return this;
   }

   public PaneBuilder setChildren(final Node... children) {
      this.children = checkNotNull(children);
      return this;
   }

   public Pane build() {
      Pane pane = new Pane();
      if (width > 0.0) pane.setPrefWidth(width);
      if (height > 0.0) pane.setPrefHeight(height);
      if (styleClass != null && !styleClass.isEmpty()) pane.getStyleClass().add(styleClass);
      if (children != null && children.length > 0) pane.getChildren().addAll(children);
      return pane;
   }
}
