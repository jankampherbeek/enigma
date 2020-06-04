/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Creates a TableView, based on the Builder pattern.
 */
public class TableViewBuilder {

   private double prefHeight;
   private double prefWidth;
   private TableColumn[] columns;

   public TableViewBuilder setPrefHeight(final double prefHeight) {
      checkArgument(prefHeight > 0.0);
      this.prefHeight = prefHeight;
      return this;
   }

   public TableViewBuilder setPrefWidth(final double prefWidth) {
      checkArgument(prefWidth > 0.0);
      this.prefWidth = prefWidth;
      return this;
   }

   public TableViewBuilder setColumns(final TableColumn... columns) {
      checkArgument(null != columns && columns.length > 0);
      this.columns = columns;
      return this;
   }

   public TableView build() {
      TableView tableView = new TableView();
      if (prefHeight > 0.0) tableView.setPrefHeight(prefHeight);
      if (prefWidth > 0.0) tableView.setPrefWidth(prefWidth);
      if (null != columns && columns.length > 0) tableView.getColumns().addAll(columns);
      return tableView;
   }


}
