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
import com.radixpro.enigma.statistics.ui.domain.ReferencePoints;
import com.radixpro.enigma.statistics.ui.domain.ScenMinMaxFe;
import com.radixpro.enigma.statistics.ui.domain.ScenarioFe;
import com.radixpro.enigma.statistics.ui.domain.StatsMinMaxTypesFe;
import com.radixpro.enigma.ui.creators.*;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.GAP;
import static com.radixpro.enigma.ui.shared.UiDictionary.TITLE_HEIGHT;

public class ScenMinMaxNew {

   private static final double HEIGHT = 250.0;
   private static final double WIDTH = 600.0;
   private static final double HALFWIDTH = 300.0;
   private final StatsFacade facade;
   private Stage stage;
   private Pane paneTitle;
   private Pane paneName;
   private Pane paneMinMaxType;
   private Pane paneRefPoints;
   private Pane paneCelObjects;
   private Pane paneMundanePoints;
   private ComboBox cbMinMaxTypes;
   private ComboBox cbRefPoints;
   private CheckComboBox ccbCelObjects;
   private CheckComboBox ccbMundanePoints;
   private TextField tfDivision;
   private Button btnSave;
   private String scenName;
   private String scenDescr;
   private String projName;
   private String typeName;

   public ScenMinMaxNew(@NotNull final StatsFacade facade) {
      this.facade = facade;
   }

   public void show(@NotNull final String name, @NotNull final String descr, @NotNull final String projName, @NotNull final String typeName) {
      this.scenName = name;
      this.scenDescr = descr;
      this.projName = projName;
      this.typeName = typeName;
      stage = new Stage();
      initialize();
      populate();
      stage.setTitle(Rosetta.getText("ui.stats.scenminmaxnew.title"));
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private void initialize() {
      Label lblTitle = new LabelBuilder("ui.stats.scenminmaxnew.title").setPrefWidth(WIDTH).setStyleClass("titletext").build();
      paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setWidth(WIDTH).setStyleClass("titlepane").setChildren(lblTitle).build();
      Label lblName = new LabelBuilder("").setPrefWidth(WIDTH).setText(scenName).build();
      paneName = new PaneBuilder().setWidth(WIDTH).setHeight(25.0).setChildren(lblName).build();
      Label lblMinMaxType = new LabelBuilder("ui.stats.scenminmaxnew.minmaxtype").setPrefWidth(HALFWIDTH).build();
      cbMinMaxTypes = new ComboBox();
      cbMinMaxTypes.setPrefWidth(HALFWIDTH);
      HBox hbMinMaxType = new HBoxBuilder().setPrefWidth(WIDTH).setPrefHeight(30.0).setChildren(lblMinMaxType, cbMinMaxTypes).build();
      paneMinMaxType = new PaneBuilder().setHeight(30.0).setWidth(WIDTH).setChildren(hbMinMaxType).build();
      Label lblRefPoints = new LabelBuilder("ui.stats.scenminmaxnew.refpoints").setPrefWidth(HALFWIDTH).build();
      cbRefPoints = new ComboBox();
      cbRefPoints.setPrefWidth(HALFWIDTH);
      HBox hbRefPoints = new HBoxBuilder().setPrefWidth(WIDTH).setPrefHeight(30.0).setChildren(lblRefPoints, cbRefPoints).build();
      paneRefPoints = new PaneBuilder().setHeight(30.0).setWidth(WIDTH).setChildren(hbRefPoints).build();
      Label lblCelPoints = new LabelBuilder("ui.stats.scenminmaxnew.celpoints").setPrefWidth(HALFWIDTH).build();
      ccbCelObjects = new CheckComboBoxBuilder().setPrefWidth(HALFWIDTH).build();
      HBox hbCelObjects = new HBoxBuilder().setPrefHeight(30.0).setPrefWidth(WIDTH).setChildren(lblCelPoints, ccbCelObjects).build();
      paneCelObjects = new PaneBuilder().setHeight(30.0).setWidth(WIDTH).setChildren(hbCelObjects).build();
      Label lblMundanePoints = new LabelBuilder("ui.stats.scenminmaxnew.mundanepoints").setPrefWidth(HALFWIDTH).build();
      ccbMundanePoints = new CheckComboBoxBuilder().setPrefWidth(HALFWIDTH).build();
      HBox hbMundanePoints = new HBoxBuilder().setPrefHeight(30.0).setPrefWidth(WIDTH).setChildren(lblMundanePoints, ccbMundanePoints).build();
      paneMundanePoints = new PaneBuilder().setHeight(30.0).setWidth(WIDTH).setChildren(hbMundanePoints).build();
   }

   private void populate() {
      final StatsMinMaxTypesFe[] valuesSMMT = StatsMinMaxTypesFe.values();
      for (StatsMinMaxTypesFe smmt : valuesSMMT) {
         cbMinMaxTypes.getItems().add(Rosetta.getText(smmt.getRbKey()));
      }
      cbMinMaxTypes.getSelectionModel().selectFirst();
      final ReferencePoints[] valuesRP = ReferencePoints.values();
      for (ReferencePoints rp : valuesRP) {
         cbRefPoints.getItems().add(Rosetta.getText(rp.getRbKey()));
      }
      cbRefPoints.getSelectionModel().selectFirst();
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
      return ccbCelObjects.getCheckModel().getCheckedIndices().size() > 0 || ccbMundanePoints.getCheckModel().getCheckedIndices().size() > 0;
   }

   private VBox createVBox() {
      return new VBoxBuilder().setHeight(HEIGHT).setWidth(WIDTH + 10.0).setPadding(GAP).setChildren(
            paneTitle,
            paneName,
            paneMinMaxType,
            paneCelObjects,
            paneRefPoints,
            paneMundanePoints,
            createPaneBtnBar()
      ).build();
   }

   private Pane createPaneBtnBar() {
      Button btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).setFocusTraversable(true).build();
      btnHelp.setOnAction(e -> onHelp());
      Button btnCancel = new ButtonBuilder("ui.shared.btn.cancel").setDisabled(false).setFocusTraversable(true).build();
      btnCancel.setOnAction(e -> stage.close());
      btnSave = new ButtonBuilder("ui.shared.btn.save").setDisabled(false).setFocusTraversable(true).build();
      btnSave.setOnAction(e -> onSave());
      ButtonBar btnBar = new ButtonBarBuilder().setButtons(btnCancel, btnHelp, btnSave).build();
      return new PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(btnBar).build();
   }

   private void onSave() {
      if (validInput()) {
         int minMaxTypeIndex = cbMinMaxTypes.getSelectionModel().getSelectedIndex();
         String minMaxTypeName = StatsMinMaxTypesFe.values()[minMaxTypeIndex].name();
         int refPointIndex = cbRefPoints.getSelectionModel().getSelectedIndex();
         String refPoint = ReferencePoints.values()[refPointIndex].name();
         CelObjects[] celObjectValues = CelObjects.values();
         List<String> celObjectNames = new ArrayList<>();
         final ObservableList checkedCelObjectIndexes = ccbCelObjects.getCheckModel().getCheckedIndices();
         for (Object index : checkedCelObjectIndexes) {
            CelObjects currentCo = celObjectValues[(int) index];
            celObjectNames.add(currentCo.name());
         }
         List<String> mundPointNames = new ArrayList<>();
         MundanePoints[] mundPointValues = MundanePoints.values();
         final ObservableList checkMundPointIndexes = ccbMundanePoints.getCheckModel().getCheckedIndices();
         for (Object index : checkMundPointIndexes) {
            MundanePoints currentMp = mundPointValues[(int) index];
            mundPointNames.add(currentMp.name());
         }
         ScenarioFe scenario = new ScenMinMaxFe(scenName, scenDescr, projName, typeName, minMaxTypeName, refPoint, celObjectNames, mundPointNames);
         facade.writeScenario(scenario);
      }
   }

   private void onHelp() {
      // todo show helpscreen
   }


}
