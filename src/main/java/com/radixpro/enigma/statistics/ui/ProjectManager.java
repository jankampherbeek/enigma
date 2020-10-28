/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.share.ui.domain.TableViewString;
import com.radixpro.enigma.ui.creators.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

public class ProjectManager {

   private static final double HEIGHT = 400.0;
   private static final double WIDTH = 600.0;
   private final ScenarioNew scenarioNew;
   private Stage stage;
   private Label lblName;
   private Pane paneTitle;
   private Pane paneSubTitle;
   private Pane paneName;
   private TableView tableView;
   private String projName;
   private StatsFacade facade;

   public ProjectManager(@NotNull final StatsFacade facade, @NotNull final ScenarioNew scenarioNew) {
      this.facade = facade;
      this.scenarioNew = scenarioNew;
   }

   public void show(@NotNull final String projName) {
      this.projName = projName;
      stage = new Stage();
      initialize();
      stage.setTitle(Rosetta.getText("ui.stats.projman.title"));
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private void initialize() {
      Label lblTitle = new LabelBuilder("ui.stats.projman.title").setPrefWidth(WIDTH).setStyleClass("titletext").build();
      paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setWidth(WIDTH).setStyleClass("titlepane").setChildren(lblTitle).build();
      lblName = new LabelBuilder("").setText(projName).build();
      paneName = new PaneBuilder().setWidth(WIDTH).setHeight(25.0).setChildren(lblName).build();
      Label lblSubTitle = new LabelBuilder("ui.stats.projman.subtitlescen").setPrefWidth(WIDTH).setStyleClass("subtitletext").build();
      paneSubTitle = new PaneBuilder().setWidth(WIDTH).setHeight(SUBTITLE_HEIGHT).setStyleClass("subtitlepane").setChildren(lblSubTitle).build();
      tableView = createTableView();
   }


   private VBox createVBox() {
      return new VBoxBuilder().setHeight(HEIGHT).setWidth(WIDTH).setPadding(GAP).setChildren(
            paneTitle,
            paneName,
            paneSubTitle,
            tableView,
            createPaneBtnBarScenarios(),
            createPaneButtonBarGeneral()
      ).build();
   }

   private TableView createTableView() {
      TableColumn colName = new TableColumn<>(Rosetta.getText("ui.general.name"));
      colName.setPrefWidth(550.0);
      colName.setCellValueFactory(new PropertyValueFactory<>("text"));
      TableView tvProj = new TableViewBuilder().setPrefHeight(200.0).setPrefWidth(WIDTH).setColumns(colName).build();
      tvProj.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      tvProj.getItems().addAll(scenarioNames());
      return tvProj;
   }

   private List<TableViewString> scenarioNames() {
      List<String> scenarios = facade.readScenarioNames(projName);
      List<TableViewString> scenariosForTv = new ArrayList<>();
      for (String scenario : scenarios) {
         scenariosForTv.add(new TableViewString(scenario));
      }
      return scenariosForTv;
   }

   private Pane createPaneBtnBarScenarios() {
      Button btnDelete = new ButtonBuilder("ui.shared.btn.delete").setDisabled(true).setFocusTraversable(false).build();
      // TODO onClick btnDelete
      Button btnDetails = new ButtonBuilder("ui.shared.btn.details").setDisabled(true).setFocusTraversable(false).build();
      // TODO onClick btnDetails
      Button btnRun = new ButtonBuilder("ui.stats.projman.run").setDisabled(true).setFocusTraversable(false).build();
      // TODO onClick btnRun
      Button btnNew = new ButtonBuilder("ui.shared.btn.new").setDisabled(false).setFocusTraversable(true).build();
      btnNew.setOnAction(e -> onNewScenario());
      ButtonBar buttonBar = new ButtonBarBuilder().setButtons(btnDelete, btnDetails, btnRun, btnNew).build();
      return new PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(buttonBar).build();
   }

   private Pane createPaneButtonBarGeneral() {
      Button btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).setFocusTraversable(true).build();
      btnHelp.setOnAction(e -> onHelp());
      Button btnClose = new ButtonBuilder("ui.shared.btn.exit").setDisabled(false).setFocusTraversable(true).build();
      btnClose.setOnAction(e -> stage.close());
      ButtonBar buttonBar = new ButtonBarBuilder().setButtons(btnHelp, btnClose).build();
      return new PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(buttonBar).build();
   }

   private void onNewScenario() {
      scenarioNew.show(projName);
   }

   private void onHelp() {
      // todo show helpscreen
   }

}
