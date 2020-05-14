/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs.screens;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.configs.screens.helpers.PropertiesForConfig;
import com.radixpro.enigma.ui.configs.screens.helpers.PropertiesTableForConfig;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.factories.ButtonFactory;
import com.radixpro.enigma.ui.shared.factories.LabelFactory;
import com.radixpro.enigma.ui.shared.factories.PaneFactory;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.ui.shared.StyleDictionary.STYLESHEET;

/**
 * Presentation of details for a specific configuration.
 */
public class ConfigDetails {

   private static final Logger LOG = Logger.getLogger(ConfigDetails.class);
   private static final double OUTER_WIDTH = 512.0;
   private static final double INNER_WIDTH = 500.0;
   private static final double HEIGHT = 600.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double BTN_PANE_HEIGHT = 30.0;
   private static final double TV_HEIGHT = 450.0;
   private static final double SEPARATOR_HEIGHT = 20.0;
   private static final double GAP = 6.0;
   private final Rosetta rosetta;
   private final String configName;
   private final PropertiesForConfig propertiesForConfig;
   private Stage stage;

   public ConfigDetails(final String configName, final PropertiesForConfig propertiesForConfig, final Rosetta rosetta) {
      this.rosetta = checkNotNull(rosetta);
      this.configName = checkNotNull(configName);
      this.propertiesForConfig = checkNotNull(propertiesForConfig);
      stage = new Stage();
      showDetails();
      LOG.info("ConfigDetails initialized for config: " + configName);
   }

   private void showDetails() {
      stage = new Stage();
      stage.setMinHeight(HEIGHT);
      stage.setMinWidth(OUTER_WIDTH);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle(rosetta.getText("ui.configs.details.title"));
      stage.setScene(new Scene(createVBox()));
      stage.show();
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.setPadding(new Insets(GAP, GAP, GAP, GAP));
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(OUTER_WIDTH);
      vBox.setPrefHeight(HEIGHT);
      vBox.getChildren().addAll(createPaneTitle(), createPaneSubTitle(), createPaneData(), createPaneSeparator(), createButtonBar());
      return vBox;
   }

   private Pane createPaneTitle() {
      Pane pane = PaneFactory.createPane(TITLE_HEIGHT, OUTER_WIDTH, "titlepane");
      pane.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.configs.details.title"), "titletext", OUTER_WIDTH));
      return pane;
   }

   private Pane createPaneSubTitle() {
      Pane pane = PaneFactory.createPane(TITLE_HEIGHT, OUTER_WIDTH, "subtitlepane");
      pane.getChildren().add(LabelFactory.createLabel(configName, "subtitletext", OUTER_WIDTH));
      return pane;
   }

   private Pane createPaneData() {
      Pane pane = PaneFactory.createPane(TV_HEIGHT, INNER_WIDTH);
      pane.getChildren().add(createTableView());
      return pane;
   }

   private Pane createPaneSeparator() {
      return PaneFactory.createPane(SEPARATOR_HEIGHT, OUTER_WIDTH);
   }

   private TableView<PresentableProperty> createTableView() {
      return new PropertiesTableForConfig().getTableView(TV_HEIGHT, INNER_WIDTH, propertiesForConfig.getProperties());
   }

   private ButtonBar createButtonBar() {
      ButtonBar buttonBar = new ButtonBar();
      Button btnHelp = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.help"), false);
      Button btnExit = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.exit"), false);
      btnHelp.setOnAction(click -> onHelp());
      btnExit.setOnAction(click -> onExit());
      buttonBar.getButtons().add(btnHelp);
      buttonBar.getButtons().add(btnExit);
      return buttonBar;
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.configdetails.title"), rosetta.getHelpText("help.configdetails.content"));
   }

   private void onExit() {
      stage.close();
   }


}
