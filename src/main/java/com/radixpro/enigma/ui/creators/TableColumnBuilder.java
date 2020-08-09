/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a TableView, based on the Builder pattern.
 */
public class TableColumnBuilder {

   private String text;
   private PropertyValueFactory cellValueFactory;

   public TableColumnBuilder setText(final String text) {
      checkArgument(null != text && !text.isEmpty());
      this.text = text;
      return this;
   }

   public TableColumnBuilder setCellValueFactory(PropertyValueFactory cellValueFactory) {
      checkNotNull(cellValueFactory);
      this.cellValueFactory = cellValueFactory;
      return this;
   }


   public TableColumn build() {
      TableColumn tableColumn = new TableColumn();
      if (null != text && !text.isEmpty()) tableColumn.setText(text);
      if (null != cellValueFactory) tableColumn.setCellValueFactory(cellValueFactory);
      return tableColumn;
   }


}
