/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.ui.creators.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.ui.shared.UiDictionary.GAP;
import static com.radixpro.enigma.ui.shared.UiDictionary.TITLE_HEIGHT;

public class ScenarioNew {

   private static final double HEIGHT = 400.0;
   private static final double WIDTH = 600.0;
   private Stage stage;
   private final ScenarioRangeNew scenarioRangeNew;
   private Pane paneTitle;
   private Pane paneName;
   private Pane paneNameInput;
   private Pane paneDescription;
   private Pane paneDescriptionInput;
   private Pane paneScenarioType;
   private Pane paneScenarioTypeInput;
   private TextField tfName;
   private TextArea taName;
   private ComboBox cbScenarioType;

   public ScenarioNew(@NotNull final ScenarioRangeNew scenarioRangeNew) {
      this.scenarioRangeNew = scenarioRangeNew;
   }

   public void show() {
      stage = new Stage();
      initialize();
      stage.setTitle(Rosetta.getText("ui.stats.scennew.title"));
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private void initialize() {
      Label lblTitle = new LabelBuilder("ui.stats.scennew.title").setPrefWidth(WIDTH).setStyleClass("titletext").build();
      paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setWidth(WIDTH).setStyleClass("titlepane").setChildren(lblTitle).build();
      Label lblName = new LabelBuilder("ui.shared.lbl.name").build();
      paneName = new PaneBuilder().setWidth(WIDTH).setHeight(25.0).setChildren(lblName).build();
      tfName = new TextFieldBuilder().setPrefWidth(WIDTH).build();
      paneNameInput = new PaneBuilder().setWidth(WIDTH).setChildren(tfName).build();
      Label lblDescription = new LabelBuilder("ui.shared.lbl.description").build();
      paneDescription = new PaneBuilder().setWidth(WIDTH).setChildren(lblDescription).build();
      taName = new TextArea();
      taName.setWrapText(true);
      taName.setPrefRowCount(4);
      paneDescriptionInput = new PaneBuilder().setWidth(WIDTH).setHeight(100.0).setChildren(taName).build();
      Label lblScenarioType = new LabelBuilder("ui.stats.scennew.scentype").setPrefWidth(WIDTH).build();
      paneScenarioType = new PaneBuilder().setWidth(WIDTH).setChildren(lblScenarioType).build();
      cbScenarioType = new ComboBox();
      cbScenarioType.setPrefWidth(WIDTH);
      paneScenarioTypeInput = new PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(cbScenarioType).build();
   }

   private VBox createVBox() {
      return new VBoxBuilder().setHeight(HEIGHT).setWidth(WIDTH).setPadding(GAP).setChildren(
            paneTitle,
            paneName,
            paneNameInput,
            paneDescription,
            paneDescriptionInput,
            paneScenarioType,
            paneScenarioTypeInput,
            createPaneBtnBar()
      ).build();
   }

   private Pane createPaneBtnBar() {
      Button btnCreate = new ButtonBuilder("ui.shared.btn.save").setDisabled(false).setFocusTraversable(true).build();   // todo initial disabled
      // todo create cconditional actions based on scenario type
      btnCreate.setOnAction(e -> scenarioRangeNew.show());
      Button btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).setFocusTraversable(true).build();
      btnHelp.setOnAction(e -> onHelp());
      Button btnCancel = new ButtonBuilder("ui.shared.btn.cancel").setDisabled(false).setFocusTraversable(true).build();
      btnCancel.setOnAction(e -> stage.close());
      ButtonBar btnBar = new ButtonBarBuilder().setButtons(btnCancel, btnHelp, btnCreate).build();
      return new PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(btnBar).build();
   }

   private void onHelp() {
      // todo show helpscreen
   }

}
