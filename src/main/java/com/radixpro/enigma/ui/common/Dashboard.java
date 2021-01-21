/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.common;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.shared.common.EnigmaDictionary;
import com.radixpro.enigma.statistics.ui.StatsStart;
import com.radixpro.enigma.ui.creators.ButtonBuilderObs;
import com.radixpro.enigma.ui.creators.LabelBuilderObs;
import com.radixpro.enigma.ui.creators.PaneBuilder;
import com.radixpro.enigma.ui.screens.ChartsStart;
import com.radixpro.enigma.ui.shared.Help;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.ui.shared.UiDictionary.STYLESHEET;

public class Dashboard {

   private static final double GAP = 6.0;
   private final ChartsStart chartsStart;
   private final StatsStart statsStart;
   private Stage stage;

   public Dashboard(@NotNull final ChartsStart chartsStart, @NotNull final StatsStart statsStart) {
      this.chartsStart = chartsStart;
      this.statsStart = statsStart;
   }

   public void showDashboard() {
      stage = new Stage();
      ButtonBar buttonBar = createButtonBar();
      ImageView imageView = createImage();

      Label lblInstruct = new LabelBuilderObs("ui.db.instruct").build();
      Label lblDescription = new LabelBuilderObs("").setText(Rosetta.getText("ui.db.describe") + ": " + EnigmaDictionary.VERSION).setLayoutX(20.0).
            setLayoutY(28.0).setStyleClass("descriptiontext").build();
      Label lblTitle = new LabelBuilderObs("ui.db.title").setLayoutX(247.0).setLayoutY(9.0).setStyleClass("titletext").build();
      Pane titlePane = new PaneBuilder().setHeight(57.0).setWidth(620.0).setStyleClass("titlepane").build();
      Pane descriptionPane = new PaneBuilder().setHeight(185.0).setWidth(120.0).setStyleClass("descriptionpane").build();

      titlePane.getChildren().add(lblTitle);
      descriptionPane.getChildren().add(lblDescription);

      BorderPane.setAlignment(titlePane, Pos.CENTER);
      BorderPane.setAlignment(descriptionPane, Pos.CENTER);
      BorderPane.setAlignment(lblInstruct, Pos.CENTER_LEFT);
      BorderPane.setAlignment(imageView, Pos.CENTER);
      BorderPane.setAlignment(buttonBar, Pos.CENTER);

      BorderPane borderPane = new BorderPane();
      borderPane.setPrefHeight(250.0);
      borderPane.setPrefWidth(620.0);
      borderPane.getStylesheets().add(STYLESHEET);
      borderPane.setTop(titlePane);
      borderPane.setLeft(imageView);
      borderPane.setRight(descriptionPane);
      borderPane.setCenter(lblInstruct);
      borderPane.setBottom(buttonBar);


      stage.setMinHeight(250.0);
      stage.setMinWidth(620.0);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle(Rosetta.getText("ui.db.title"));
      stage.setScene(new Scene(borderPane));
      stage.show();
   }


   private ButtonBar createButtonBar() {
      ButtonBar buttonBar = new ButtonBar();
      buttonBar.setPadding(new Insets(GAP, GAP, GAP, GAP));
      buttonBar.setPrefWidth(600.0);

      Button btnHelp = new ButtonBuilderObs("ui.shared.btn.help").setDisabled(false).build();
      Button btnCharts = new ButtonBuilderObs("ui.db.btn.charts").setDisabled(false).build();
      Button btnPeriods = new ButtonBuilderObs("ui.db.btn.periods").setDisabled(true).build();
      Button btnStats = new ButtonBuilderObs("ui.db.btn.stats").setDisabled(false).build();
      Button btnTools = new ButtonBuilderObs("ui.db.btn.tools").setDisabled(true).build();
      Button btnLanguage = new ButtonBuilderObs("ui.db.btn.language").setDisabled(false).build();
      Button btnExit = new ButtonBuilderObs("ui.shared.btn.exit").setDisabled(false).build();

      btnHelp.setOnAction(click -> onHelp());
      btnLanguage.setOnAction(click -> onLanguage());
      btnCharts.setOnAction(click -> onCharts());
      btnStats.setOnAction(click -> onStats());
      btnExit.setOnAction(click -> onExit());

      buttonBar.getButtons().add(btnHelp);
      buttonBar.getButtons().add(btnCharts);
      buttonBar.getButtons().add(btnPeriods);
      buttonBar.getButtons().add(btnStats);
      buttonBar.getButtons().add(btnTools);
      buttonBar.getButtons().add(btnLanguage);
      buttonBar.getButtons().add(btnExit);

      return buttonBar;
   }

   private ImageView createImage() {
      Image image = new Image("img/ziggurat.png");
      ImageView imageView = new ImageView(image);
      imageView.setFitWidth(223.0);
      imageView.setFitHeight(86.0);
      imageView.setPickOnBounds(true);
      imageView.setPreserveRatio(true);
      return imageView;
   }

   private void onHelp() {
      new Help(Rosetta.getHelpText("help.shareddb.title"), Rosetta.getHelpText("help.shareddb.content"));
   }

   private void onLanguage() {
      String language = Rosetta.getLocale().getLanguage();
      Rosetta.setLanguage(language.equalsIgnoreCase("du") ? "en" : "du");
      stage.close();
      showDashboard();
   }

   private void onCharts() {
      chartsStart.show();
   }

   private void onStats() {
      statsStart.show();
   }

   private void onExit() {
      Platform.exit();
   }


}
