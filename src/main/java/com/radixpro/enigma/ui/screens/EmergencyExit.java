/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.ui.creators.LabelBuilderObs;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.radixpro.enigma.ui.shared.UiDictionary.STYLESHEET;

/**
 * Controller for popup that handles terminating Enigma in case of an unrecoverable error.
 */
public class EmergencyExit {

   private static final double WIDTH = 600.0;
   private static final double HEIGHT = 400.0;
   private static final double GAP = 6.0;
   private final Label lblExplanation;
   private final String explanationTxt;

   public EmergencyExit(final String causeOfError) {
      this.explanationTxt = causeOfError;
      lblExplanation = new Label();
      Stage stage = new Stage();
      stage.setWidth(WIDTH);
      stage.setHeight(HEIGHT);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.setPadding(new Insets(GAP, GAP, GAP, GAP));
      vBox.getStylesheets().add(STYLESHEET);
      vBox.getChildren().add(0, createErrorTitlePane());
      vBox.getChildren().add(1, createExplanationPane());
      vBox.getChildren().add(2, createBtnBar());
      return vBox;
   }

   private Pane createErrorTitlePane() {
      Pane pane = new Pane();
      pane.setPrefHeight(80.0);
      pane.getStyleClass().add("errorpane");
      Label errorTxt = new LabelBuilderObs("emergencyexit.title").setStyleClass("errortitletext").setPrefWidth(WIDTH).build();
      pane.getChildren().add(errorTxt);
      return pane;
   }

   private Pane createExplanationPane() {
      Pane pane = new Pane();
      pane.setPrefHeight(160.0);
      lblExplanation.setWrapText(true);
      lblExplanation.setText(Rosetta.getText("emergencyexit.intro") + explanationTxt);
      pane.getChildren().add(lblExplanation);
      return pane;
   }

   private ButtonBar createBtnBar() {
      ButtonBar btnBar = new ButtonBar();
      Button btnExit = new Button(Rosetta.getText("emergencyexit.close"));
      btnExit.setOnAction(click -> onExit());
      btnBar.getButtons().add(btnExit);
      return btnBar;
   }

   void onExit() {
      System.exit(0);
   }

   public void initialize() {
      lblExplanation.setText(Rosetta.getText("emergencyexit.intro") + explanationTxt);
   }

}
