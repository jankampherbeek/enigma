/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.scene.control.ListView;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Creates a ListView, based on the Builder pattern.
 */
public class ListViewBuilder {

   private double height;
   private double width;
   private String styleClass;

   public ListViewBuilder setWidth(double width) {
      checkArgument(0.0 < width);
      this.width = width;
      return this;
   }

   public ListViewBuilder setHeight(double height) {
      checkArgument(0.0 < height);
      this.height = height;
      return this;
   }

   public ListViewBuilder setStyleClass(String styleClass) {
      checkArgument(null != styleClass && !styleClass.isBlank());
      this.styleClass = styleClass;
      return this;
   }

   public ListView build() {
      ListView listView = new ListView();
      if (0.0 < height) listView.setPrefHeight(height);
      if (0.0 < width) listView.setPrefWidth(width);
      if (null != styleClass) listView.getStyleClass().add(styleClass);
      return listView;
   }

}
