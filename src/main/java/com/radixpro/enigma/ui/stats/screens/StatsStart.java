/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.stats.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StatsStart {

   private static final double WIDTH = 600.0;
   private Stage stage;

   public StatsStart() {
      showIt();
   }

   private void showIt() {
      stage = new Stage();
      stage.setWidth(WIDTH);
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.getChildren().addAll(createTitle(), createButtonBar());
      return vBox;
   }

   private Label createTitle() {
      return new Label("Stats Start Screen");
   }

   private ButtonBar createButtonBar() {
      ButtonBar buttonBar = new ButtonBar();
      Button exitBtn = new Button("Exit");
      Button searchBtn = new Button("Search project");
      Button newButton = new Button("New project");
      Button editButton = new Button("Edit project");
      Button runButton = new Button("Run test");
      searchBtn.setOnAction(click -> onSearch());
      buttonBar.getButtons().addAll(exitBtn, searchBtn, newButton, editButton, runButton);
      return buttonBar;
   }


   private void onSearch() {
      new StatsSearch();
   }


}
