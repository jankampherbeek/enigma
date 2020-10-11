/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.statistics.core.DataFileDescription;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.shared.Help;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class StatsDataDetail {

   private static final double OUTER_WIDTH = 512.0;
   private static final double INNER_WIDTH = 500.0;
   private static final double HEIGHT = 280.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double SEPARATOR_HEIGHT = 20.0;
   private static final double GAP = 6.0;
   private Stage stage;
   private Button btnHelp;
   private Button btnExit;

   public void show(@NotNull final DataFileDescription dataFileDescription) {
      populateStage(dataFileDescription);
   }


   private void populateStage(final DataFileDescription dataFileDescription) {
      btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      btnExit = new ButtonBuilder("ui.shared.btn.exit").setDisabled(false).build();
      ButtonBar buttonBar = new ButtonBar();
      buttonBar.getButtons().addAll(btnHelp, btnExit);
      btnHelp.setOnAction(click -> onHelp());
      btnExit.setOnAction(click -> onExit());
      Label lblTitle = new LabelBuilder("ui.stats.datafile.detail.lbltitle").setPrefWidth(OUTER_WIDTH).setStyleClass("titletext").build();
      Pane paneTitle = new PaneBuilder().setWidth(OUTER_WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(lblTitle).build();
      Label lblName = new LabelBuilder("ui.stats.datafile.detail.name").setPrefWidth(200.0).build();
      Label lblDescr = new LabelBuilder("ui.stats.datafile.detail.description").setPrefWidth(200.0).build();
      Label lblNrOfRecords = new LabelBuilder("ui.stats.datafile.detail.nrofrecords").setPrefWidth(200.0).build();
      Label lblNameValue = new LabelBuilder("").setText(dataFileDescription.getName()).build();
      Label lblDescrValue = new LabelBuilder("").setText(dataFileDescription.getDescription()).build();
      Label lblNrOfRecordsValue = new LabelBuilder("").setText(Integer.toString(dataFileDescription.getNrOfRecords())).build();
      HBox nameBox = new HBoxBuilder().setPrefWidth(INNER_WIDTH).setPrefHeight(40.0).setChildren(lblName, lblNameValue).build();
      HBox descrBox = new HBoxBuilder().setPrefWidth(INNER_WIDTH).setPrefHeight(40.0).setChildren(lblDescr, lblDescrValue).build();
      HBox nrOfRBox = new HBoxBuilder().setPrefWidth(INNER_WIDTH).setPrefHeight(40.0).setChildren(lblNrOfRecords, lblNrOfRecordsValue).build();
      Pane paneSeparator = new PaneBuilder().setWidth(OUTER_WIDTH).setHeight(SEPARATOR_HEIGHT).build();
      VBox vBox = new VBoxBuilder().setWidth(OUTER_WIDTH).setHeight(HEIGHT).setPadding(GAP).
            setChildren(paneTitle, nameBox, descrBox, nrOfRBox, paneSeparator, buttonBar).build();
      stage = new StageBuilder().setMinWidth(OUTER_WIDTH).setMinHeight(HEIGHT).setModality(Modality.APPLICATION_MODAL)
            .setTitle(Rosetta.getText("ui.stats.datafile.detail.title")).setScene(new Scene(vBox)).build();
      stage.show();
   }

   private void onExit() {
      stage.close();
   }

   private void onHelp() {
      new Help(Rosetta.getHelpText("help.statsdatadetail.title"), Rosetta.getHelpText("help.statsdatadetail.content"));
   }


}
