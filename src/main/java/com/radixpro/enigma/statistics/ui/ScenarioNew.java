/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes;
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
   private final ScenMinMaxNew scenMinMaxNew;
   private Pane paneTitle;
   private Pane paneName;
   private Pane paneNameInput;
   private Pane paneDescription;
   private Pane paneDescriptionInput;
   private Pane paneScenarioType;
   private Pane paneScenarioTypeInput;
   private TextField tfName;
   private TextArea taDescr;
   private ComboBox cbScenarioType;
   private String projName;

   public ScenarioNew(@NotNull final ScenarioRangeNew scenarioRangeNew, @NotNull ScenMinMaxNew scenMinMaxNew) {
      this.scenarioRangeNew = scenarioRangeNew;
      this.scenMinMaxNew = scenMinMaxNew;
   }

   public void show(@NotNull final String projName) {
      this.projName = projName;
      stage = new Stage();
      initialize();
      stage.setTitle(Rosetta.getText("ui.stats.scennew.title"));
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private void initialize() {
      Label lblTitle = new LabelBuilderObs("ui.stats.scennew.title").setPrefWidth(WIDTH).setStyleClass("titletext").build();
      paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setWidth(WIDTH).setStyleClass("titlepane").setChildren(lblTitle).build();
      Label lblName = new LabelBuilderObs("ui.shared.lbl.name").build();
      paneName = new PaneBuilder().setWidth(WIDTH).setHeight(25.0).setChildren(lblName).build();
      tfName = new TextFieldBuilder().setPrefWidth(WIDTH).build();
      paneNameInput = new PaneBuilder().setWidth(WIDTH).setChildren(tfName).build();
      Label lblDescription = new LabelBuilderObs("ui.shared.lbl.description").build();
      paneDescription = new PaneBuilder().setWidth(WIDTH).setChildren(lblDescription).build();
      taDescr = new TextArea();
      taDescr.setWrapText(true);
      taDescr.setPrefRowCount(4);
      paneDescriptionInput = new PaneBuilder().setWidth(WIDTH).setHeight(100.0).setChildren(taDescr).build();
      Label lblScenarioType = new LabelBuilderObs("ui.stats.scennew.scentype").setPrefWidth(WIDTH).build();
      paneScenarioType = new PaneBuilder().setWidth(WIDTH).setChildren(lblScenarioType).build();
      cbScenarioType = new ComboBox();
      cbScenarioType.setPrefWidth(WIDTH);
      paneScenarioTypeInput = new PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(cbScenarioType).build();
      populateScenarios();
   }

   private void populateScenarios() {
      ScenarioTypes[] scenTypes = ScenarioTypes.values();
      for (ScenarioTypes scenType : scenTypes) {
         cbScenarioType.getItems().add(Rosetta.getText(scenType.getRbName()));
      }
      cbScenarioType.getSelectionModel().selectFirst();
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
      Button btnContinue = new ButtonBuilderObs("ui.shared.btn.continue").setDisabled(false).setFocusTraversable(true).build();   // todo initial disabled
      btnContinue.setOnAction(e -> onContinue());
      Button btnHelp = new ButtonBuilderObs("ui.shared.btn.help").setDisabled(false).setFocusTraversable(true).build();
      btnHelp.setOnAction(e -> onHelp());
      Button btnCancel = new ButtonBuilderObs("ui.shared.btn.cancel").setDisabled(false).setFocusTraversable(true).build();
      btnCancel.setOnAction(e -> stage.close());
      ButtonBar btnBar = new ButtonBarBuilder().setButtons(btnCancel, btnHelp, btnContinue).build();
      return new PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(btnBar).build();
   }

   private void onContinue() {
      int typeIndex = cbScenarioType.getSelectionModel().getSelectedIndex();
      String typeName = ScenarioTypes.values()[typeIndex].name();

      if (!tfName.getText().isEmpty() && !taDescr.getText().isEmpty()) {
         int index = cbScenarioType.getSelectionModel().getSelectedIndex();
         switch (index) {
            case 0:
               scenarioRangeNew.show(tfName.getText(), taDescr.getText(), projName, typeName);
               stage.close();
               break;
            case 1:
               scenMinMaxNew.show(tfName.getText(), taDescr.getText(), projName, typeName);
               stage.close();
               break;
            default: // todo
               break;
         }
      }
   }

   private void onHelp() {
      // todo show helpscreen
   }

}
