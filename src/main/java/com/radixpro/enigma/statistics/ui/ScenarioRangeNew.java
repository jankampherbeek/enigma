/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.astronomy.ui.domain.CelObjects;
import com.radixpro.enigma.astronomy.ui.domain.MundanePoints;
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes;
import com.radixpro.enigma.ui.creators.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import static com.radixpro.enigma.ui.shared.UiDictionary.GAP;
import static com.radixpro.enigma.ui.shared.UiDictionary.TITLE_HEIGHT;

public class ScenarioRangeNew {

   private static final double HEIGHT = 250.0;
   private static final double WIDTH = 600.0;
   private static final double HALFWIDTH = 300.0;
   private Stage stage;
   private Pane paneTitle;
   private Pane paneName;
   private Pane paneRangeType;
   private Pane paneCelObjects;
   private Pane paneMundanePoints;
   private ComboBox cbRangeTypes;
   private CheckComboBox ccbCelObjects;
   private CheckComboBox ccbMundanePoints;
   private TextField tfDivision;
   private Button btnSave;


   public ScenarioRangeNew() {

   }

   public void show() {
      stage = new Stage();
      initialize();
      populate();
      stage.setTitle(Rosetta.getText("ui.stats.scenrangenew.title"));
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private void initialize() {
      Label lblTitle = new LabelBuilder("ui.stats.scenrangenew.title").setPrefWidth(WIDTH).setStyleClass("titletext").build();
      paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setWidth(WIDTH).setStyleClass("titlepane").setChildren(lblTitle).build();
      Label lblName = new LabelBuilder("").setPrefWidth(WIDTH).setText("TODO name for scenario").build();
      paneName = new PaneBuilder().setWidth(WIDTH).setHeight(25.0).setChildren(lblName).build();
      Label lblRangeType = new LabelBuilder("ui.stats.scenrangenew.rangetype").setPrefWidth(HALFWIDTH).build();
      cbRangeTypes = new ComboBox();
      cbRangeTypes.setPrefWidth(HALFWIDTH);
      HBox hbRangeType = new HBoxBuilder().setPrefWidth(WIDTH).setPrefHeight(30.0).setChildren(lblRangeType, cbRangeTypes).build();
      paneRangeType = new PaneBuilder().setHeight(30.0).setWidth(WIDTH).setChildren(hbRangeType).build();
      Label lblCelPoints = new LabelBuilder("ui.stats.scenrangenew.celpoints").setPrefWidth(HALFWIDTH).build();
      ccbCelObjects = new CheckComboBoxBuilder().setPrefWidth(HALFWIDTH).build();
      HBox hbCelObjects = new HBoxBuilder().setPrefHeight(30.0).setPrefWidth(WIDTH).setChildren(lblCelPoints, ccbCelObjects).build();
      paneCelObjects = new PaneBuilder().setHeight(30.0).setWidth(WIDTH).setChildren(hbCelObjects).build();
      Label lblMundanePoints = new LabelBuilder("ui.stats.scenrangenew.mundanepoints").setPrefWidth(HALFWIDTH).build();
      ccbMundanePoints = new CheckComboBoxBuilder().setPrefWidth(HALFWIDTH).build();
      HBox hbMundanePoints = new HBoxBuilder().setPrefHeight(30.0).setPrefWidth(WIDTH).setChildren(lblMundanePoints, ccbMundanePoints).build();
      paneMundanePoints = new PaneBuilder().setHeight(30.0).setWidth(WIDTH).setChildren(hbMundanePoints).build();

   }

   private void populate() {
      // TODO save values from enums and use the indexes of the selected items to collect the correct values.
      final StatsRangeTypes[] valuesSRT = StatsRangeTypes.values();
      for (StatsRangeTypes srt : valuesSRT) {
         cbRangeTypes.getItems().add(Rosetta.getText(srt.getRbKey()));
      }
      final CelObjects[] valuesCO = CelObjects.values();
      for (CelObjects co : valuesCO) {
         ccbCelObjects.getItems().add(Rosetta.getText(co.getRbKey()));
      }
      final MundanePoints[] valuesMP = MundanePoints.values();
      for (MundanePoints mp : valuesMP) {
         ccbMundanePoints.getItems().add(Rosetta.getText(mp.getRbKey()));
      }
   }

   private boolean validInput() {
      return !tfDivision.getText().isBlank()
            // &&  tfDivision.getText is a valid Integer
            && (ccbCelObjects.getCheckModel().getCheckedIndices().size() > 0 || ccbMundanePoints.getCheckModel().getCheckedIndices().size() > 0)
            // add check for types
            ;
   }

   private VBox createVBox() {
      return new VBoxBuilder().setHeight(HEIGHT).setWidth(WIDTH + 10.0).setPadding(GAP).setChildren(
            paneTitle,
            paneName,
            paneRangeType,
            paneCelObjects,
            paneMundanePoints,
            createPaneBtnBar()
      ).build();
   }

   private Pane createPaneBtnBar() {
      Button btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).setFocusTraversable(true).build();
      btnHelp.setOnAction(e -> onHelp());
      Button btnCancel = new ButtonBuilder("ui.shared.btn.cancel").setDisabled(false).setFocusTraversable(true).build();
      btnCancel.setOnAction(e -> stage.close());
      btnSave = new ButtonBuilder("ui.shared.btn.save").setDisabled(true).setFocusTraversable(false).build();
      // TODO define action
      ButtonBar btnBar = new ButtonBarBuilder().setButtons(btnCancel, btnHelp, btnSave).build();
      return new PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(btnBar).build();
   }

   private void onHelp() {
      // todo show helpscreen
   }

}