/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.scene.control.ListView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for ListViews.
 */
@SuppressWarnings("rawtypes")
public class ListViewFactory {

   private ListViewFactory() {
      // prevent instantiation
   }

   public static ListView createListView(final double height, final double width, final String styleClass) {
      checkNotNull(styleClass);
      ListView listView = new ListView();
      listView.setPrefHeight(height);
      listView.setPrefWidth(width);
      listView.getStyleClass().add(styleClass);
      return listView;
   }
}
