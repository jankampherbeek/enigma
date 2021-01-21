/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.statistics.ui.domain.ScenarioFe;
import com.radixpro.enigma.statistics.ui.helpers.ScenDetailsText;
import com.radixpro.enigma.ui.creators.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.ui.shared.UiDictionary.GAP;
import static com.radixpro.enigma.ui.shared.UiDictionary.TITLE_HEIGHT;

public class ScenarioDetails {

   private static final double HEIGHT = 500.0;
   private static final double HEIGHT_DETAILS = 300.0;
   private static final double WIDTH = 600.0;
   private final StatsFacade facade;
   private final ScenDetailsText scenDetailsText;
   private Stage stage;
   private Pane paneTitle;
   private String projName;
   private String scenName;

   public ScenarioDetails(@NotNull final StatsFacade facade, @NotNull final ScenDetailsText scenDetailsText) {
      this.facade = facade;
      this.scenDetailsText = scenDetailsText;
   }

   public void show(@NotNull String scenName, @NotNull final String projName) {
      this.projName = projName;
      this.scenName = scenName;
      stage = new Stage();
      initialize();
      stage.setTitle(Rosetta.getText("ui.stats.scendetail.title"));
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private void initialize() {
      Label lblTitle = new LabelBuilderObs("ui.stats.scendetail.title").setPrefWidth(WIDTH).setStyleClass("titletext").build();
      paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setWidth(WIDTH).setStyleClass("titlepane").setChildren(lblTitle).build();
   }


   private VBox createVBox() {
      return new VBoxBuilder().setHeight(HEIGHT).setWidth(WIDTH).setPadding(GAP).setChildren(
            paneTitle,
            createPaneDetails(),
            createPaneButtonBar()
      ).build();
   }

   private Pane createPaneDetails() {
      ScenarioFe scenario = facade.readScenario(scenName, projName);
      String detailText = scenDetailsText.createText(scenario);
      Label lblDetails = new LabelBuilderObs("").setText(detailText).setPrefWidth(WIDTH).build();
      lblDetails.setWrapText(true);
      return new PaneBuilder().setHeight(HEIGHT_DETAILS).setWidth(WIDTH).setChildren(lblDetails).build();
   }

   private Pane createPaneButtonBar() {
      Button btnHelp = new ButtonBuilderObs("ui.shared.btn.help").setDisabled(false).setFocusTraversable(true).build();
      btnHelp.setOnAction(e -> onHelp());
      Button btnClose = new ButtonBuilderObs("ui.shared.btn.exit").setDisabled(false).setFocusTraversable(true).build();
      btnClose.setOnAction(e -> stage.close());
      ButtonBar buttonBar = new ButtonBarBuilder().setButtons(btnHelp, btnClose).build();
      return new PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(buttonBar).build();
   }

   private void onHelp() {
      // todo create help
   }

}
