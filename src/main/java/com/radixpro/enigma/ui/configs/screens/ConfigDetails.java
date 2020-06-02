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
import com.radixpro.enigma.ui.shared.factories.ButtonBuilder;
import com.radixpro.enigma.ui.shared.factories.LabelBuilder;
import com.radixpro.enigma.ui.shared.factories.PaneBuilder;
import com.radixpro.enigma.ui.shared.factories.VBoxBuilder;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableProperty;
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

   public ConfigDetails(final Stage stage, final String configName, final PropertiesForConfig propertiesForConfig, final Rosetta rosetta) {
      this.stage = checkNotNull(stage);
      this.rosetta = checkNotNull(rosetta);
      this.configName = checkNotNull(configName);
      this.propertiesForConfig = checkNotNull(propertiesForConfig);
      createNavigation();
      showDetails();
      LOG.info("ConfigDetails initialized for config: " + configName);
   }

   private void createNavigation() {
      btnHelp = new ButtonBuilder(rosetta.getText("ui.shared.btn.help")).setDisabled(false).build();
      btnExit = new ButtonBuilder(rosetta.getText("ui.shared.btn.exit")).setDisabled(false).build();
      btnHelp.setOnAction(click -> onHelp());
      btnExit.setOnAction(click -> onExit());
   }

   private void showDetails() {
      stage.setMinHeight(HEIGHT);
      stage.setMinWidth(OUTER_WIDTH);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle(rosetta.getText("ui.configs.details.title"));
      stage.setScene(new Scene(createVBox()));
      stage.show();
   }

   private VBox createVBox() {
      return new VBoxBuilder(OUTER_WIDTH)
            .setHeight(HEIGHT)
            .setPadding(GAP)
            .setChildren(createPaneTitle(), createPaneSubTitle(), createPaneData(), createPaneSeparator(), createButtonBar())
            .build();
   }

   private Pane createPaneTitle() {
      return new PaneBuilder().setWidth(OUTER_WIDTH).setHeight(TITLE_HEIGHT)
            .setStyleClass("titlepane")
            .setChildren(new LabelBuilder(rosetta.getText("ui.configs.details.title"))
                  .setPrefWidth(OUTER_WIDTH)
                  .setStyleClass("titletext").build()).build();
   }

   private Pane createPaneSubTitle() {
      return new PaneBuilder().setWidth(OUTER_WIDTH).setHeight(TITLE_HEIGHT)
            .setStyleClass("subtitlepane")
            .setChildren(new LabelBuilder(configName)
                  .setPrefWidth(OUTER_WIDTH)
                  .setStyleClass("subtitletext").build()).build();
   }

   private Pane createPaneData() {
      return new PaneBuilder().setWidth(INNER_WIDTH).setHeight(TV_HEIGHT).setChildren(createTableView()).build();
   }

   private Pane createPaneSeparator() {
      return new PaneBuilder().setWidth(OUTER_WIDTH).setHeight(SEPARATOR_HEIGHT).build();
   }

   private TableView<PresentableProperty> createTableView() {
      return new PropertiesTableForConfig().getTableView(TV_HEIGHT, INNER_WIDTH, propertiesForConfig.getProperties());
   }

   private ButtonBar createButtonBar() {
      ButtonBar buttonBar = new ButtonBar();
      buttonBar.getButtons().addAll(btnHelp, btnExit);
      return buttonBar;
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.configdetails.title"), rosetta.getHelpText("help.configdetails.content"));
   }

   private void onExit() {
      stage.close();
   }


}
