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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.radixpro.enigma.ui.shared.UiDictionary.GAP;
import static com.radixpro.enigma.ui.shared.UiDictionary.TITLE_HEIGHT;

public class ScenarioRangeNew {

   private static final double HEIGHT = 200.0;
   private static final double WIDTH = 600.0;
   private Stage stage;
   private Pane paneTitle;
   private Pane paneName;
   private Pane paneRangeType;
   private Pane paneDivision;
   private ComboBox cbRangeTypes;
   private TextField tfDivision;

   public ScenarioRangeNew() {

   }

   public void show() {
      stage = new Stage();
      initialize();
      stage.setTitle(Rosetta.getText("ui.stats.scenrangenew.title"));
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private void initialize() {
      Label lblTitle = new LabelBuilder("ui.stats.scenrangenew.title").setPrefWidth(WIDTH).setStyleClass("titletext").build();
      paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setWidth(WIDTH).setStyleClass("titlepane").setChildren(lblTitle).build();
      Label lblName = new LabelBuilder("").setText("TODO name for scenario").build();
      paneName = new PaneBuilder().setWidth(WIDTH).setHeight(25.0).setChildren(lblName).build();
      Label lblRangeType = new LabelBuilder("ui.stats.scenrangenew.rangetype").setPrefWidth(WIDTH / 2.0).build();
      cbRangeTypes = new ComboBox();
      cbRangeTypes.setPrefWidth(WIDTH / 2.0);
      HBox hbRangeType = new HBoxBuilder().setPrefWidth(WIDTH / 2.0).setPrefHeight(30.0).setChildren(lblRangeType, cbRangeTypes).build();
      paneRangeType = new PaneBuilder().setHeight(30.0).setWidth(WIDTH).setChildren(hbRangeType).build();
      Label lblDivision = new LabelBuilder("ui.stats.scenrangenew.division").setPrefHeight(30.0).setPrefWidth(WIDTH / 2.0).build();
      tfDivision = new TextFieldBuilder().setPrefWidth(WIDTH / 2.0).build();
      HBox hbDivision = new HBoxBuilder().setPrefHeight(30.0).setPrefWidth(WIDTH / 2.0).setChildren(lblDivision, tfDivision).build();
      paneDivision = new PaneBuilder().setHeight(30.0).setWidth(WIDTH).setChildren(hbDivision).build();

   }

   private VBox createVBox() {
      return new VBoxBuilder().setHeight(HEIGHT).setWidth(WIDTH).setPadding(GAP).setChildren(
            paneTitle,
            paneName,
            paneRangeType,
            paneDivision,
            createPaneBtnBar()
      ).build();
   }

   private Pane createPaneBtnBar() {
      Button btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).setFocusTraversable(true).build();
      btnHelp.setOnAction(e -> onHelp());
      Button btnCancel = new ButtonBuilder("ui.shared.btn.cancel").setDisabled(false).setFocusTraversable(true).build();
      btnCancel.setOnAction(e -> stage.close());
      Button btnSave = new ButtonBuilder("ui.shared.btn.save").setDisabled(true).setFocusTraversable(false).build();
      // TODO define action
      ButtonBar btnBar = new ButtonBarBuilder().setButtons(btnCancel, btnHelp, btnSave).build();
      return new PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(btnBar).build();
   }

   private void onHelp() {
      // todo show helpscreen
   }

}
