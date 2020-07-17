/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs.screens;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.charts.ChartsSessionState;
import com.radixpro.enigma.ui.configs.screens.helpers.PropertiesForConfig;
import com.radixpro.enigma.ui.configs.screens.helpers.PropertiesTableForConfig;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.factories.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Presentation of details for a specific configuration.
 */
public class ConfigDetails {

   private static final Logger LOG = Logger.getLogger(ConfigDetails.class);
   private static final double OUTER_WIDTH = 512.0;
   private static final double INNER_WIDTH = 500.0;
   private static final double HEIGHT = 600.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double TV_HEIGHT = 450.0;
   private static final double SEPARATOR_HEIGHT = 20.0;
   private static final double GAP = 6.0;
   private final Rosetta rosetta;
   private final String configName;
   private final PropertiesForConfig propertiesForConfig;
   private Stage stage;
   private Button btnHelp;
   private Button btnExit;


   public ConfigDetails(final PropertiesForConfig propertiesForConfig, final Rosetta rosetta, final ChartsSessionState state) {
      this.rosetta = checkNotNull(rosetta);
      checkNotNull(state);
      this.configName = state.getSelectedConfig().getName();
      this.propertiesForConfig = checkNotNull(propertiesForConfig);
      populateStage();
      defineListeners();
      stage.show();
      LOG.info("ConfigDetails initialized for config: " + configName);
   }

   private void populateStage() {
      btnHelp = new ButtonBuilder(rosetta.getText("ui.shared.btn.help")).setDisabled(false).build();
      btnExit = new ButtonBuilder(rosetta.getText("ui.shared.btn.exit")).setDisabled(false).build();
      ButtonBar buttonBar = new ButtonBar();                       // TODO replace with builder
      buttonBar.getButtons().addAll(btnHelp, btnExit);
      Label lblTitle = new LabelBuilder(rosetta.getText("ui.configs.details.title")).setPrefWidth(OUTER_WIDTH).setStyleClass("titletext").build();
      Label lblSubTitle = new LabelBuilder(configName).setPrefWidth(OUTER_WIDTH).setStyleClass("subtitletext").build();
      TableView tableView = new PropertiesTableForConfig().getTableView(TV_HEIGHT, INNER_WIDTH, propertiesForConfig.getProperties());  // TODO replace with builder
      Pane paneTitle = new PaneBuilder().setWidth(OUTER_WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(lblTitle).build();
      Pane paneSubTitle = new PaneBuilder().setWidth(OUTER_WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("subtitlepane").setChildren(lblSubTitle).build();
      Pane paneData = new PaneBuilder().setWidth(INNER_WIDTH).setHeight(TV_HEIGHT).setChildren(tableView).build();
      Pane paneSeparator = new PaneBuilder().setWidth(OUTER_WIDTH).setHeight(SEPARATOR_HEIGHT).build();
      VBox vBox = new VBoxBuilder().setWidth(OUTER_WIDTH).setHeight(HEIGHT).setPadding(GAP).setChildren(paneTitle, paneSubTitle, paneData, paneSeparator, buttonBar)
            .build();
      stage = new StageBuilder().setMinWidth(OUTER_WIDTH).setMinHeight(HEIGHT).setModality(Modality.APPLICATION_MODAL)
            .setTitle(rosetta.getText("ui.configs.details.title")).setScene(new Scene(vBox)).build();
   }

   private void defineListeners() {
      btnHelp.setOnAction(click -> onHelp());
      btnExit.setOnAction(click -> onExit());
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.configdetails.title"), rosetta.getHelpText("help.configdetails.content"));
   }

   private void onExit() {
      stage.close();
   }

}
