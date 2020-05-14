/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs.screens.helpers;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A table with details of properties for a configuration.
 */
public class PropertiesTableForConfig {

   private final Rosetta rosetta;

   public PropertiesTableForConfig() {
      rosetta = Rosetta.getRosetta();
   }


   /**
    * Constructs the table
    *
    * @param height     Preferred height of the table.
    * @param width      Preferred width of the table.
    * @param properties List with actual properties.
    * @return Constructed table with properties int he form of a TableView.
    */
   public TableView<PresentableProperty> getTableView(final double height, final double width,
                                                      List<PresentableProperty> properties) {
      checkNotNull(properties);
      return createTableView(height, width, properties);
   }


   private TableView<PresentableProperty> createTableView(double height, double width,
                                                          List<PresentableProperty> properties) {
      TableView<PresentableProperty> tableView = new TableView<>();
      tableView.setPrefHeight(height);
      tableView.setPrefWidth(width);
      TableColumn<PresentableProperty, String> propertyColumn = new TableColumn<>(rosetta.getText("config.propertyname"));
      propertyColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
      TableColumn<PresentableProperty, String> valueColumn = new TableColumn<>(rosetta.getText(("config.propertyvalue")));
      valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
      tableView.getColumns().add(propertyColumn);
      tableView.getColumns().add(valueColumn);

      for (PresentableProperty prop : properties) {
         tableView.getItems().add(prop);
      }
      return tableView;
   }

}
